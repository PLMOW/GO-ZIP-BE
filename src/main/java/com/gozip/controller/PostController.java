package com.gozip.controller;

import com.gozip.dto.PostRequestDto;
import com.gozip.dto.PostResponseDto;
import com.gozip.security.MemberDetailsImpl;
import com.gozip.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/product")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                                      @RequestPart (value = "data") PostRequestDto postDto,
                                                      @RequestPart(value = "image") List<MultipartFile> pictures) {
        return postService.createPost(memberDetails, postDto, pictures);
    }

    // 게시글 선택 조회
    @GetMapping("/product/{id}")
    public PostRequestDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
}
