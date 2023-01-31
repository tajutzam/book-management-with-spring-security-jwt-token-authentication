package com.zam.springsecurityjwt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookUpdateDto {

    private Integer id;
    private String description;
    private String bookName;
    private Integer price;
    private Integer pages;
    private Integer category;
    private String author;

}
