package com.gozip.dto;

import com.gozip.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public PostRequestDto(Post post) {
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.house_type = post.getHouseType();
        this.city = post.getAddress().getCity();
        this.town = post.getAddress().getTown();
        this.street = post.getAddress().getStreet();
    }
}
