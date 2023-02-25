package com.gozip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private String msg;
    private long id;

    public PostResponseDto(String msg, long id) {
        this.msg = msg;
        this.id = id;
    }
}
