package com.gozip.controller;

import com.gozip.dto.PostRequestDto;
import com.gozip.dto.PostResponseDto;
import com.gozip.security.MemberDetailsImpl;
import com.gozip.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/product")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody PostRequestDto postDto) {
        return postService.createPost(memberDetails, postDto);
    }

    // 게시글 선택 조회
    @GetMapping("/product/{id}")
    public PostRequestDto getPost(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @PathVariable Long id) {
        return postService.getPost(memberDetails, id);
    }
}
