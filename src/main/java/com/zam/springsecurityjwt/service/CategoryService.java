package com.zam.springsecurityjwt.service;

import com.zam.springsecurityjwt.dto.CategoryUpdateDTO;
import com.zam.springsecurityjwt.entity.Category;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Optional<List<Category>> findAll(){
        List<Category> categoryList = categoryRepository.findAll();
        if(categoryList.size() == 0){
            return Optional.empty();
        }
        return Optional.of(categoryList);
    }

    public Optional<Category> add(Category category){
        try {
            if(findByCategory(category.getName()).isPresent()){
                return Optional.empty();
            }
            Category save = categoryRepository.save(category);
            return Optional.of(save);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            throw  new ApiRequestException(e.getMessage() , e.getCause());
        }
    }

    public Optional<Category> findByCategory(String categoryName){
        return categoryRepository.findByName(categoryName);
    }

    public Optional<Category> update(CategoryUpdateDTO categoryUpdateDTO){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryUpdateDTO.getId());
            if(optionalCategory.isPresent()){
                var category = Category.builder().
                        id(categoryUpdateDTO.getId()).name(categoryUpdateDTO.getName()).books(optionalCategory.get().getBooks()).
                build();
                Category updated = categoryRepository.save(category);
                return Optional.of(updated);
            }
            return Optional.empty();

    }

    public boolean deleteById(Integer id){
        try {
            Optional<Category> optional = categoryRepository.findById(id);
            if(optional.isPresent()){
                categoryRepository.delete(optional.get());
                return true;
            }
            return false;
        }catch (Exception e){
            throw new ApiRequestException(e.getMessage() , e.getCause());
        }
    }

    public Optional<Category> findById(Integer id){
        return categoryRepository.findById(id);
    }
}
