package com.zam.springsecurityjwt.service;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class BookResponse {
    private String bookName;
    private String description;
    private Integer pages;
    private Integer price;
}
