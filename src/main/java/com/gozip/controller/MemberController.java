package com.gozip.controller;

import com.gozip.dto.*;
import com.gozip.entity.Member;
import com.gozip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://13.125.193.128:8080")
public class MemberController {

    private final MemberService memberService;

    // 로그인 구현. 일단 반환타입 Member로
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        return memberService.login(request, response);
    }

    // 회원가입 요청
    @PostMapping("/signup")
    public StateDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    @GetMapping("/tryget")
    public StateDto tryget(){
        StateDto stateDto = new StateDto("200");
        return stateDto;
    }
}
