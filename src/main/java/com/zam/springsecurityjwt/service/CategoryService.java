package com.zam.springsecurityjwt.service;

import com.zam.springsecurityjwt.dto.CategoryUpdateDTO;
import com.zam.springsecurityjwt.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Optional<List<Category>> findAll();
    public Optional<Category> add(Category category);
    public Optional<Category> findByCategory(String categoryName);
    public Optional<Category> update(CategoryUpdateDTO categoryUpdateDTO);
    public boolean deleteById(Integer id);
    public Optional<Category> findById(Integer id);

}
