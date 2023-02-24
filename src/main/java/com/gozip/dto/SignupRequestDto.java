package com.gozip.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private Long memberId;

    // 정규식 조건 추가 필요
    private String email;

    private String password;
}
