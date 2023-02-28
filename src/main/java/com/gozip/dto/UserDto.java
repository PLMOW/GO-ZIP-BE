package com.gozip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserDto {

    @Getter
    @Setter
    public static class LoginReq {
        private String email;
        private String password;
        private String nickname;
    }

    @Getter
    @Builder
    public static class LoginRes {
        private String email;
        private String nickname;
        private boolean ok;
    }

    @Getter
    @Setter
    public static class SignupReq {
        private String email;
        private String password;
        private String nickname;
        private boolean admin;
        private String adminToken;
    }

}
