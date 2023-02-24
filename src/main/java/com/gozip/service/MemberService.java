package com.gozip.service;

import com.gozip.dto.LoginRequestDto;
import com.gozip.dto.StateDto;
import com.gozip.entity.Member;
import com.gozip.jwt.JwtUtil;
import com.gozip.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    // 로그인 구현
    @Transactional
    public StateDto login(LoginRequestDto request, HttpServletResponse response) {
        // 유저 이메일, 비밀번호 가져오기
        String email = request.getEmail();
        String password = request.getPassword();
        // 아이디 & 비밀번호 유효성 검사
        if(!StringUtils.hasText(email) || !StringUtils.hasText(password)){
            throw new IllegalArgumentException("입력값이 유효하지 않습니다.");
        }
        // 회원 찾기
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        // 비밀번호 일치여부
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 토큰 발급
        response.addHeader(jwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(member.getEmail()));
        // 상태 반환
        return new StateDto("OK");
    }



    // 회원가입 구현

}
