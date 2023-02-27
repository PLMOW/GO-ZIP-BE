package com.gozip.service;

import com.gozip.dto.SignupRequestDto;
import com.gozip.entity.Member;
import com.gozip.entity.MemberRoleEnum;
import com.gozip.exception.customException.InvalidDataException;
import com.gozip.repository.MemberRepository;
import com.gozip.dto.LoginRequestDto;
import com.gozip.dto.StateDto;
import com.gozip.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    // 로그인 구현
    @Transactional(readOnly = true)
    public StateDto login(LoginRequestDto request, HttpServletResponse response) {
        // 유저 이메일, 비밀번호 가져오기
        String email = request.getEmail();
        String password = request.getPassword();
        // 아이디 & 비밀번호 유효성 검사
        if(!StringUtils.hasText(email) || !StringUtils.hasText(password)){
            throw new InvalidDataException("입력값이 유효하지 않습니다.");
        }
        // 회원 찾기
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new InvalidDataException("아이디가 존재하지 않습니다.")
        );
        // 비밀번호 일치여부
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidDataException("비밀번호가 일치하지 않습니다.");
        }
        // 토큰 발급
        response.addHeader(jwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(member.getEmail(), member.getRole()));
        // 상태 반환
        return new StateDto("OK");
    }



    // 회원가입 구현
    @Transactional
    public StateDto signup(SignupRequestDto signupRequestDto) {

        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();


        // 회원 중복 여부 확인
        Optional<Member> findEmail = memberRepository.findMemberByEmail(email);
        if (findEmail.isPresent()) {
            throw new InvalidDataException("이미 등록된 이메일 입니다.");
        }

        // 회원, 관리자(중개인) 확인
        MemberRoleEnum role = MemberRoleEnum.MEMBER;
        if(signupRequestDto.isAdmin()) {
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 토큰이 틀려 등록이 불가능 합니다.");
            }
            role = MemberRoleEnum.ADMIN;
        }


        Member member = new Member(email, password, nickname, role);
        memberRepository.save(member);

        return new StateDto("OK");
    }

}
