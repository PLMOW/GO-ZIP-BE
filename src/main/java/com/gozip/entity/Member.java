package com.gozip.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String password;

    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(String email, String password, String nickname, MemberRoleEnum role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
