package com.gozip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberDto {

    @Getter
    @Setter
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    public static class LoginRes {
        private Long member_id;
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
