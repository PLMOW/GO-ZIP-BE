package com.gozip.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    public Member(String email, String password, MemberRoleEnum role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
