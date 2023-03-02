package com.gozip.repository.custom;

import com.gozip.dto.PostDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostDto> searchAllPosts(String city, String town, String street, String houseType);
}
