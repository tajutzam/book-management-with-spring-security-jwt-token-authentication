package com.zam.springsecurityjwt.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryUpdateDTO {
    private Integer id;
    private String name;
}
