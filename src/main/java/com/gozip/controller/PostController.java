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
                                                      @RequestPart(value = "data") PostRequestDto postDto,
                                                      @RequestPart(value = "image") List<MultipartFile> pictures) {
        return postService.createPost(memberDetails, postDto, pictures);
    }

    // 게시글 선택 조회
    @GetMapping("/product/{id}")
    public PostRequestDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 상세페이지 전체 조회
    @GetMapping("/product/search")
    public List<PostRequestDto> getAllPosts(
            @RequestParam(value = "city", defaultValue = "") String city,
            @RequestParam(value = "town", defaultValue = "") String town,
            @RequestParam(value = "street", defaultValue = "") String street,
            @RequestParam(value = "house_type", defaultValue = "") String houseType
            ) {
        return postService.getAllPosts(city, town, street, houseType);
    }

    // 게시글 수정
    @PutMapping("/product/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                     @PathVariable Long id,
                                     @RequestPart(value = "data") PostRequestDto postDto,
                                     @RequestPart(value = "image") List<MultipartFile> pictures){
        return postService.updatePost(memberDetails, id, postDto, pictures);
    }

    // 게시글 삭제
    @DeleteMapping("/product/{id}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long id, @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return postService.deletePost(memberDetails, id);
    }



}
