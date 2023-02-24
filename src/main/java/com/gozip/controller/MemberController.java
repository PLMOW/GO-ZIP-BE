package com.gozip.controller;

import com.gozip.dto.LoginRequestDto;
import com.gozip.dto.StateDto;
import com.gozip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
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

}
