package com.zam.springsecurityjwt.service;

import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.dto.BookResponse;
import com.zam.springsecurityjwt.dto.BookUpdateDto;
import com.zam.springsecurityjwt.entity.Book;
import com.zam.springsecurityjwt.entity.Category;
import com.zam.springsecurityjwt.repo.BookRepository;
import com.zam.springsecurityjwt.repo.CategoryRepository;
import com.zam.springsecurityjwt.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<BookResponse> findAll(){
        List<Book> all = bookRepository.findAll();
        List<BookResponse> response = new ArrayList<>();
        all.forEach(book -> {
            response.add(
                    BookResponse.builder()
                            .bookName(book.getBookName())
                            .description(book.getDescription())
                            .price(book.getPrice())
                            .pages(book.getPages())
                            .categoryName(book.getCategory().getName())
                            .build()
            );
        });
        return response;
    }
    public Optional<Book> addBook(BookDTO bookDTO){
        Optional<Category> categoryOptional = categoryRepository.findById(bookDTO.getCategory());
        if(categoryOptional.isPresent()){
            var book = Book.builder().
                    bookName(bookDTO.getBookName())
                    .description(bookDTO.getDescription())
                    .pages(bookDTO.getPages())
                    .category(categoryOptional.get())
                    .price(bookDTO.getPrice()).
                    build();
            Optional<Book> optional = bookRepository.findByBookName(bookDTO.getBookName());
            if(optional.isPresent()){
                return Optional.empty();
            }
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }
    public Optional<Book> findById(Integer uuid){
        return bookRepository.findById(uuid);
    }
    public Optional<Book> updateBook(BookUpdateDto bookUpdateDto){
        Optional<Book> optionalBook = findById(bookUpdateDto.getId());
        if(optionalBook.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(
                Book.builder().id(bookUpdateDto.getId()).bookName(bookUpdateDto.getBookName())
                        .price(bookUpdateDto.getPrice())
                        .pages(bookUpdateDto.getPages())
                        .build()
        ));
    }

    public boolean deleteById(Integer uuid){
        Optional<Book> optionalBook = findById(uuid);
        if(optionalBook.isEmpty()){
            return false;
        }
        bookRepository.deleteById(uuid);
        return true;
    }


}
