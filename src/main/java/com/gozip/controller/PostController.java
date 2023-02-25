package com.gozip.controller;

import com.gozip.dto.PostRequestDto;
import com.gozip.dto.PostResponseDto;
import com.gozip.security.MemberDetailsImpl;
import com.gozip.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/product/form")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal MemberDetailsImpl memberDetails, @RequestBody PostRequestDto postRequestDto) {
        return postService.createPost(memberDetails, postRequestDto);
    }
}
