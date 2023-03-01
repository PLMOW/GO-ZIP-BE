package com.gozip.controller;

import com.gozip.dto.*;
import com.gozip.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://13.125.193.128:8080")
public class MemberController {

    private final MemberService memberService;

    // 로그인 구현
    @PostMapping("/login")
    public ResponseEntity<MemberDto.LoginRes> login(@RequestBody MemberDto.LoginReq request, HttpServletResponse response) {
        return memberService.login(request, response);
    }

    // 회원가입 요청
    @PostMapping("/signup")
    public ResponseEntity<StateDto> signup(@RequestBody MemberDto.SignupReq signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    @GetMapping("/tryget")
    public StateDto tryget(){
        StateDto stateDto = new StateDto("200",200);
        return stateDto;
    }
}
