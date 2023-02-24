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
//@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // 로그인 구현. 일단 반환타입 Member로
    @PostMapping("/login")
    public StateDto logIn(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        return memberService.login(request, response);
    }



    // 회원가입 구현

    // 회원가입 페이지
    @GetMapping("/signin")
    public ModelAndView signinPage() {
        return new ModelAndView("signin");
    }

    // 회원가입 요청
    @PostMapping("/signin")
    public StateDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        System.out.println("컨트롤러 진입");
        return memberService.signin(signupRequestDto);
    }

}
