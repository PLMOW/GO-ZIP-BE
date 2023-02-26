package com.gozip.service;

import com.gozip.dto.PostRequestDto;
import com.gozip.dto.PostResponseDto;
import com.gozip.entity.Address;
import com.gozip.entity.Member;
import com.gozip.entity.Post;
import com.gozip.exception.customException.InvalidDataException;
import com.gozip.repository.MemberRepository;
import com.gozip.repository.PostRepository;
import com.gozip.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public ResponseEntity<PostResponseDto> createPost(MemberDetailsImpl memberDetails, PostRequestDto postDto) {
        // 사용자 찾아오기
        Member member = memberRepository.findMemberByEmail(memberDetails.getMember().getEmail()).orElseThrow(
                () -> new InvalidDataException("존재하지 않는 아이디 입니다.")
        );
        // 주소 저장
        Address address = new Address(postDto.getCity(), postDto.getTown(), postDto.getStreet());
        // Post 저장
        Post post = new Post(postDto, member, address);
        postRepository.save(post);
        // 상태 반환
        return ResponseEntity.ok(new PostResponseDto("ok", post.getId()));
    }

    // 선택 게시글 조회
    public PostRequestDto getPost(Long id) {
        // id로 post조회하기
        Post post = postRepository.findById(id).orElseThrow(
                () -> new InvalidDataException("게시글이 존재하지 않습니다.")
        );
        // dto로 반환
        return new PostRequestDto(post);
    }
}
