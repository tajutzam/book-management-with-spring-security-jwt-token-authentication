package com.zam.springsecurityjwt.dto;

import lombok.Data;

@Data
public class KeyDTO<T> {
    private T key;
}
