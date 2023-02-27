package com.gozip.dto;

import com.gozip.entity.Picture;
import com.gozip.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String description;
    private String house_type;
    private String city;
    private String town;
    private String street;
    private List<String> images = new ArrayList<>();

    public PostRequestDto(Post post) {
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
}
