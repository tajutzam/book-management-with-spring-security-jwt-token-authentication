package com.zam.springsecurityjwt.controller;


import com.zam.springsecurityjwt.dto.CategoryDto;
import com.zam.springsecurityjwt.dto.CategoryUpdateDTO;
import com.zam.springsecurityjwt.dto.KeyDTO;
import com.zam.springsecurityjwt.entity.Category;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.repo.CategoryRepository;
import com.zam.springsecurityjwt.service.impl.CategoryServiceImpl;
import com.zam.springsecurityjwt.util.Helper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
@SecurityRequirement(name = "javainuseapi")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        Optional<List<Category>> all = categoryService.findAll();
        return all.map(categories -> Helper.generateResponse(
                "category found", HttpStatus.OK, categories
        )).orElseGet(() -> Helper.generateResponse(
                "category empty", HttpStatus.NOT_FOUND, null
        ));
    }
    @PostMapping("/add")
    @Operation(summary = "some description", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDto categoryDto){
       try {
           Category categoryRequest = modelMapper.map(categoryDto , Category.class);
           return categoryService.add(categoryRequest)
                   .map(category -> Helper.generateResponse("Success add category", HttpStatus.CREATED, category))
                   .orElse(Helper.generateResponse("Failed to add category, there is a category with the same name", HttpStatus.BAD_REQUEST, null));
       }catch (RuntimeException e){
           System.out.println(e.getMessage());
           throw  new ApiRequestException(e.getMessage() , e.getCause());
       }
    }
    @PutMapping("/update")
    public ResponseEntity<Object> updateBook(@RequestBody CategoryUpdateDTO categoryUpdateDTO){

        Optional<Category> optionalCategory = categoryService.findById(categoryUpdateDTO.getId());
        if(optionalCategory.isPresent()){
            if(Objects.equals(categoryUpdateDTO.getName(), optionalCategory.get().getName())){
                return Helper.generateResponse("failed to update category , there are same name category" , HttpStatus.BAD_REQUEST , false);
            }
            return categoryService.update(categoryUpdateDTO)
                    .map(category -> Helper.generateResponse("Success update category", HttpStatus.OK, categoryRepository.findById(category.getId())))
                    .orElse(Helper.generateResponse("Failed to update category , Category not found", HttpStatus.NOT_FOUND, null));
        }else{
            return Helper.generateResponse("Failed to update category , Category not found", HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Integer id){
        return categoryService.findById(id)
                .map(category -> Helper.generateResponse("category found", HttpStatus.OK, category))
                .orElse(Helper.generateResponse("Category not found", HttpStatus.NOT_FOUND, null));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteById(@RequestBody KeyDTO<Integer> keyDTO){
        try {
            boolean delete = categoryService.deleteById(keyDTO.getKey());
            if(delete){
                return Helper.generateResponse("Success deleted category" , HttpStatus.OK , true);
            }
            return Helper.generateResponse("Failed to delete category  , category not found" , HttpStatus.NOT_FOUND , false);
        }catch (RuntimeException e){
            throw new ApiRequestException(e.getMessage() , e.getCause());
        }
    }
}
