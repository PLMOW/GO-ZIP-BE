package com.gozip.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String description;
    private String house_type;
    private String img;
    private String city;
    private String town;
    private String street;
}
