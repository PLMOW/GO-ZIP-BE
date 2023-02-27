package com.gozip.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private long id;

    @Column(length = 1000)
    private String pictureUrl;

    @Column(length = 1000)
    private String pictureKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Picture(List<String>list, Post post) {
        this.pictureKey = list.get(0);
        this.pictureUrl = list.get(1);
        this.post = post;
    }
}
