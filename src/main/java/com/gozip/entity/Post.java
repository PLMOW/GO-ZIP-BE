package com.gozip.entity;

import com.gozip.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String description;
    @Column(name = "house_type")
    private String houseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Picture> Pictures = new ArrayList<>();

    public Post(PostDto postRequestDto, Member member, Address address) {
        title = postRequestDto.getTitle();
        description = postRequestDto.getDescription();
        houseType = postRequestDto.getHouse_type();
        this.member = member;
        this.address = address;
    }

    public void update(PostDto postRequestDto, Address address){
        title = postRequestDto.getTitle();
        description = postRequestDto.getDescription();
        houseType = postRequestDto.getHouse_type();
        this.address = address;
    }
}
