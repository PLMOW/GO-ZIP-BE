package com.gozip.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gozip.config.aws.S3Uploader;
import com.gozip.dto.PostDto;
import com.gozip.entity.Address;
import com.gozip.entity.Member;
import com.gozip.entity.Picture;
import com.gozip.entity.Post;
import com.gozip.exception.CustomException;
import com.gozip.exception.ErrorCode;
import com.gozip.repository.MemberRepository;
import com.gozip.repository.PictureRepository;
import com.gozip.repository.PostRepository;
import com.gozip.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PictureRepository pictureRepository;
    private final S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;



    // 게시글 등록
    @Transactional
    public ResponseEntity<PostDto.Res> createPost(MemberDetailsImpl memberDetails, PostDto postDto, List<MultipartFile> pictures) {
        // 사용자 찾아오기
        Member member = memberRepository.findMemberByEmail(memberDetails.getMember().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        // 주소 저장
        Address address = new Address(postDto.getCity(), postDto.getTown(), postDto.getStreet());
        // Post 저장
        Post post = new Post(postDto, member, address);
        postRepository.save(post);
        // 사진 저장
        for (MultipartFile picture : pictures) {
            if (!picture.isEmpty()) {
                List<String> storedFileName = s3Uploader.upload(picture, "image");
                pictureRepository.save(new Picture(storedFileName, post));
            }
        }
        // 상태 반환
        return ResponseEntity.ok(
                PostDto.Res.builder()
                        .msg("ok")
                        .post_id(post.getId())
                        .build()
        );
    }

    // 선택 게시글 조회
    @Transactional
    public PostDto getPost(Long id) {
        // id로 post조회하기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_POST)
        );
        // dto로 반환
        return new PostDto(post);
    }

    public List<PostDto> getAllPosts(String city, String town, String street, String houseType) {
        return postRepository.searchAllPosts(city, town, street, houseType);
    }

    // 게시글 수정 시 이미지는 <선택 게시글 조회>에서 이미 주었기 때문에 해당 사진을 다시 업로드하는 걸로
    // 중복된 사진은 제외하여 서버 이용을 낮출 수 있지만 다음에 수정해 보자
    @Transactional
    public  ResponseEntity<PostDto> updatePost(MemberDetailsImpl memberDetails, Long postId, PostDto postDto, List<MultipartFile> pictures){

        Member member = memberRepository.findMemberByEmail(memberDetails.getMember().getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_POST)
        );

        // 포스트 작성자와 토큰 멤버 일치 확인
        if(post.getMember().getMemberId() != memberDetails.getMember().getMemberId()) {
            throw new CustomException(ErrorCode.AUTHORIZATION);
        }

        Address address = new Address(postDto.getCity(), postDto.getTown(), postDto.getStreet());

        // Post 수정
        post.update(postDto, address);


        //사진 수정(사진은 전체 삭제 후 다시 업로드)
        List<Picture> deletedPictures = pictureRepository.findAllByPost_Id(postId);

        // S3에 업로드된 사진 삭제하기
        for (Picture deletedPicture : deletedPictures) {
            String key = deletedPicture.getPictureKey();
            final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
            try{
                s3.deleteObject(bucket, key);
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        pictureRepository.deleteAllByPost_Id(postId);
        post.getPictures().clear();

//         S3에 다시 업로드
        for (MultipartFile picture : pictures) {
            if (!picture.isEmpty()) {
                List<String> storedFileName = s3Uploader.upload(picture, "image");
                Picture newPicture = new Picture(storedFileName, post);
                pictureRepository.save(newPicture);
                post.getPictures().add(newPicture);
            }
        }
        // 상태 반환
        return ResponseEntity.ok(new PostDto(post));

    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<PostDto.Res> deletePost(MemberDetailsImpl memberDetails, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_POST)
        );

        // 포스트 작성자와 토큰 멤버 일치 확인
        if(post.getMember().getMemberId() != memberDetails.getMember().getMemberId()) {
            throw new CustomException(ErrorCode.AUTHORIZATION);
        }

        // 이미지 URL 불러오기
        List<Picture> deletedPictures = pictureRepository.findPicturesByPostId(post.getId());
        // S3 이미지 삭제
        for (Picture deletedPicture : deletedPictures) {
            String key = deletedPicture.getPictureKey();
            final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
            try{
                s3.deleteObject(bucket, key);
            } catch(Exception e){
                e.printStackTrace();
            }

        }
        //포스트 삭제
        postRepository.deleteById(postId);

        return ResponseEntity.ok(
                PostDto.Res.builder()
                        .post_id(postId)
                        .msg("ok")
                        .build()
        );



    }
}
