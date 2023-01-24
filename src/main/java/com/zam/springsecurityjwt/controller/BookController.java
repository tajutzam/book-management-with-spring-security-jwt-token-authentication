package com.zam.springsecurityjwt.controller;

import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.entity.Book;
import com.zam.springsecurityjwt.dto.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(bookService.findAll()  );
    }


    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@RequestBody BookDTO bookDTO){
        Optional<Book> optional = bookService.addBook(bookDTO);

        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        }
        Map<String , Object> payload = new HashMap<>();
        payload.put("message" , "gagal menambahkan buku  , judul buku sudah tersedia");
        return ResponseEntity.ok(payload);
    }


}
