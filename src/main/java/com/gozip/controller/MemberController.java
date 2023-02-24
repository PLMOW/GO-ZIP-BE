package com.gozip.controller;

import com.gozip.entity.Member;
import com.gozip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;



    // 로그인 구현





    // 회원가입 구현

}
