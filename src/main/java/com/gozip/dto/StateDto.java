package com.gozip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StateDto {

    private String msg;

    public StateDto(String msg) {
        this.msg = msg;
    }
}
