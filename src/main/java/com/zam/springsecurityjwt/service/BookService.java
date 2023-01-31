package com.zam.springsecurityjwt.service;


import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.dto.BookResponse;
import com.zam.springsecurityjwt.dto.BookUpdateDto;
import com.zam.springsecurityjwt.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    public List<BookResponse> findAll();
    public Optional<Book> addBook(Book bookDTO);
    public Optional<Book> findById(Integer uuid);
    public Optional<Book> updateBook(BookUpdateDto bookUpdateDto);
    public boolean deleteById(Integer uuid);

}
