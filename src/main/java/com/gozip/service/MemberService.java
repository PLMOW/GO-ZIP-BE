package com.gozip.service;

import com.gozip.dto.*;
import com.gozip.entity.Member;
import com.gozip.entity.MemberRoleEnum;
import com.gozip.exception.CustomException;
import com.gozip.exception.ErrorCode;
import com.gozip.repository.MemberRepository;
import com.gozip.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberDto.LoginRes> login(MemberDto.LoginReq request, HttpServletResponse response) {
        // 유저 이메일, 비밀번호 가져오기
        String email = request.getEmail();
        String password = request.getPassword();
        // 아이디 & 비밀번호 유효성 검사
        if(!StringUtils.hasText(email) || !StringUtils.hasText(password)){
            throw new CustomException(ErrorCode.NOT_CONGITION_INSERT);
        }
        // 회원 찾기
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_MATCH_EMAIL)
        );
        // 비밀번호 일치여부
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
        // 토큰 발급
        response.addHeader(jwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(member.getEmail(), member.getRole()));
        // 상태 반환
        return ResponseEntity.ok(
                MemberDto.LoginRes.builder()
                        .member_id(member.getMemberId())
                        .email(email)
                        .nickname(member.getNickname())
                        .ok(true)
                        .build()
                );
    }



    // 회원가입 구현
    @Transactional
    public ResponseEntity<StateDto> signup(MemberDto.SignupReq signupRequestDto) {

        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();


        // 회원 중복 여부 확인
        Optional<Member> findEmail = memberRepository.findMemberByEmail(email);
        if (findEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        // 회원, 관리자(중개인) 확인
        MemberRoleEnum role = MemberRoleEnum.MEMBER;
        if(signupRequestDto.isAdmin()) {
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            }
            role = MemberRoleEnum.ADMIN;
        }


        Member member = new Member(email, password, nickname, role);
        memberRepository.save(member);

        return ResponseEntity.ok(
                StateDto.builder()
                        .status(HttpStatus.OK.value())
                        .msg("회원가입 성공")
                        .build()
        );
    }

}
