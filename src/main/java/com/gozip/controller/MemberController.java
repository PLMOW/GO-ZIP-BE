package com.gozip.controller;

import com.gozip.dto.SignupRequestDto;
import com.gozip.entity.Member;
import com.gozip.dto.LoginRequestDto;
import com.gozip.dto.StateDto;
import com.gozip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인 구현. 일단 반환타입 Member로
    @PostMapping("/login")
    public StateDto login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        return memberService.login(request, response);
    }

//     회원가입 페이지
//    @GetMapping("/signup")
//    public ModelAndView signupPage() {
//        return new ModelAndView("signin");
//    }

    // 회원가입 요청
    @PostMapping("/signup")
    public StateDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

}
