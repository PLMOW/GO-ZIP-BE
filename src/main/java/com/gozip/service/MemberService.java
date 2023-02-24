package com.gozip.service;

import com.gozip.dto.SignupRequestDto;
import com.gozip.entity.Member;
import com.gozip.repository.MemberRepository;
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

import java.util.Optional;

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

    public String signin(SignupRequestDto signupRequestDto) {

        Long memberId = signupRequestDto.getMemberId();

        String email = signupRequestDto.getEmail();

        String password = signupRequestDto.getPassword();

        // 회원 중복 여부 확인
        Optional<Member> findEmail = memberRepository.findMemberByEmail(email);
        if (findEmail.isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        }

        // 회원, 관리자(중개인) 확인
        MemberRoleEnum role = MemberRoleEnum.MEMBER;
        if(signupRequestDto.isAdmin()) {
            if(signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 토큰이 틀려 등록이 불가능 합니다.");
            }
            role = MemberRoleEnum.ADMIN;
        }

        Member member = new Member(memberId, email, password, role);
        memberRepository.save(member);

        return new StateDto("OK");
    }

}
