package com.gozip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {

    private String email;
    private String nickname;
    private boolean ok;


    public LoginResponseDto(String email, String nickname, boolean ok) {
        this.email = email;
        this.nickname = nickname;
        this.ok = ok;
    }
}
