package com.zam.springsecurityjwt.dto;

import com.zam.springsecurityjwt.entity.Category;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class BookDTO {
    private String description;
    private String bookName;
    private Integer price;
    private Integer pages;
    private Integer category;
    private String author;
    private MultipartFile file;
}
