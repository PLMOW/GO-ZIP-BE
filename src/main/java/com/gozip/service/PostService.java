package com.gozip.service;

import com.gozip.config.aws.S3Uploader;
import com.gozip.dto.PostRequestDto;
import com.gozip.dto.PostResponseDto;
import com.gozip.entity.Address;
import com.gozip.entity.Member;
import com.gozip.entity.Picture;
import com.gozip.entity.Post;
import com.gozip.exception.customException.InvalidDataException;
import com.gozip.repository.MemberRepository;
import com.gozip.repository.PictureRepository;
import com.gozip.repository.PostRepository;
import com.gozip.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
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

    // 게시글 등록
    @Transactional
    public ResponseEntity<PostResponseDto> createPost(MemberDetailsImpl memberDetails, PostRequestDto postDto, List<MultipartFile> pictures) {
        // 사용자 찾아오기
        Member member = memberRepository.findMemberByEmail(memberDetails.getMember().getEmail()).orElseThrow(
                () -> new InvalidDataException("존재하지 않는 아이디 입니다.")
        );
        // 주소 저장
        Address address = new Address(postDto.getCity(), postDto.getTown(), postDto.getStreet());
        // Post 저장
        Post post = new Post(postDto, member, address);
        postRepository.save(post);
        // 사진 저장
        for (MultipartFile picture : pictures) {
            if (!picture.isEmpty()) {
                String storedFileName = s3Uploader.upload(picture, "image");
                pictureRepository.save(new Picture(storedFileName, post));
            }
        }
        // 상태 반환
        return ResponseEntity.ok(new PostResponseDto("ok", post.getId()));
    }

    // 선택 게시글 조회
    @Transactional
    public PostRequestDto getPost(Long id) {
        // id로 post조회하기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new InvalidDataException("게시글이 존재하지 않습니다.")
        );
        // dto로 반환
        return new PostRequestDto(post);
    }

    public List<PostRequestDto> getAllPosts(String city, String town, String street) {
        return postRepository.searchAllPosts(city, town, street);
    }

    // 게시글 수정 시 이미지는 <선택 게시글 조회>에서 이미 주었기 때문에 해당 사진을 다시 업로드하는 걸로
    // 중복된 사진은 제외하여 서버 이용을 낮출 수 있지만 다음에 수정해 보자
    @Transactional
    public  ResponseEntity<PostResponseDto> updatePost(MemberDetailsImpl memberDetails, Long postId, PostRequestDto postDto, List<MultipartFile> pictures){

        Member member = memberRepository.findMemberByEmail(memberDetails.getMember().getEmail()).orElseThrow(
                () -> new InvalidDataException("존재하지 않는 아이디 입니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new InvalidDataException("존재하지 않는 게시글 입니다.")
        );

        Address address = new Address(postDto.getCity(), postDto.getTown(), postDto.getStreet());

        // Post 수정
        post.update(postDto, address);


        //사진 수정(사진은 전체 삭제 후 다시 업로드)
        pictureRepository.deleteAllByPost_Id(member.getMemberId());

        for (MultipartFile picture : pictures) {
            if (!picture.isEmpty()) {
                String storedFileName = s3Uploader.upload(picture, "image");
                pictureRepository.save(new Picture(storedFileName, post));
            }
        }
        // 상태 반환
        return ResponseEntity.ok(new PostResponseDto("ok", post.getId()));

    }

    public ResponseEntity<PostResponseDto> deletePost(MemberDetailsImpl memberDetails, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new InvalidDataException("존재하지 않는 게시글 입니다.")
        );
        //-----
        postRepository.deleteById(postId);

        return ResponseEntity.ok(new PostResponseDto("ok", postId));



    }
}
