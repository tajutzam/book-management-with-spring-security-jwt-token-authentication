package com.zam.springsecurityjwt.controller;

import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.dto.BookResponse;
import com.zam.springsecurityjwt.dto.BookUpdateDto;
import com.zam.springsecurityjwt.dto.KeyDTO;
import com.zam.springsecurityjwt.entity.Book;
import com.zam.springsecurityjwt.entity.Category;
import com.zam.springsecurityjwt.exeptions.ApiRequestException;
import com.zam.springsecurityjwt.repo.CategoryRepository;
import com.zam.springsecurityjwt.service.impl.BookServiceImpl;
import com.zam.springsecurityjwt.util.Helper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/book")
@SecurityRequirement(name = "javainuseapi")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        List<BookResponse> bookResponseList = bookService.findAll();
        if(bookResponseList.size() == 0){
            return Helper.generateResponse(
                    "empty book" , HttpStatus.NOT_FOUND , bookResponseList
            );
        }
        return Helper.generateResponse("there are book in database" , HttpStatus.OK , bookResponseList);
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@RequestBody BookDTO bookDTO){
           Optional<Book> optional = bookService.addBook(bookDTO);
           Optional<Category> categoryOptional = categoryRepository.findById(bookDTO.getCategory());
           if(categoryOptional.isPresent()){
               return optional.<ResponseEntity<Object>>map(ResponseEntity::ok)
                       .orElseGet(() -> Helper.generateResponse("failed to add book , there smae book name", HttpStatus.BAD_REQUEST, null));
           }else{
               throw new ApiRequestException("Category not found");
           }

    }
    @PostMapping("/edit")
    public ResponseEntity<Object> updateBook(@RequestBody BookUpdateDto bookUpdateDto){
      try {
          Optional<Book> updateBook = bookService.updateBook(bookUpdateDto);
          return updateBook.map(book -> Helper.generateResponse(
                  "success update book", HttpStatus.OK, book
          )).orElseGet(() -> Helper.generateResponse(
                  "Failed to update book , book not found", HttpStatus.NOT_FOUND, null
          ));
      }catch (RuntimeException e){
          throw new ApiRequestException(e.getCause().getMessage());
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") Integer id){
        return bookService.findById(id).map(book -> Helper.generateResponse(
                "book found", HttpStatus.OK, book
        )).orElse(Helper.generateResponse("book with id "+id+" not found", HttpStatus.NOT_FOUND, null));
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteById(@RequestBody KeyDTO<Integer> id){
        boolean byId = bookService.deleteById(id.getKey());
        return byId ? Helper.generateResponse(
                "Success Delete book" , HttpStatus.OK , null )
                :  Helper.generateResponse("failed delete book" , HttpStatus.NOT_FOUND
                , null);
    }

}
