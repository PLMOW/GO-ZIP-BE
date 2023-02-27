package com.gozip.repository.custom;

import com.gozip.dto.PostRequestDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostRequestDto> searchAllPosts(String city, String town, String street, String houseType);
}
