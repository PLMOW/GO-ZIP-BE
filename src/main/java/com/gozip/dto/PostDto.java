package com.gozip.dto;

import com.gozip.entity.Picture;
import com.gozip.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String house_type;
    private String city;
    private String town;
    private String street;
    List<String> images = new ArrayList<>();

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.house_type = post.getHouseType();
        this.city = post.getAddress().getCity();
        this.town = post.getAddress().getTown();
        this.street = post.getAddress().getStreet();
        List<Picture> pictures = post.getPictures();
        for (Picture picture : pictures) {
            images.add(picture.getPictureUrl());
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Res {
        private String msg;
        private long id;
    }
}
