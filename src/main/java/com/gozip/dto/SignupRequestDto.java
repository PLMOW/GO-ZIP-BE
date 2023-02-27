package com.gozip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    // 정규식 조건 추가 필요
    private String email;

    private String password;

    private String nickname;

    private boolean admin = false;

    private String adminToken = "";
}
