package com.zam.springsecurityjwt.dto;

import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.entity.Book;
import com.zam.springsecurityjwt.repo.BookRepository;
import com.zam.springsecurityjwt.service.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

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
                            .build()
            );
        });
        return response;
    }

    public Optional<Book> addBook(BookDTO bookDTO){
        var book = Book.builder().
                bookName(bookDTO.getBookName())
                        .description(bookDTO.getDescription())
                                .pages(bookDTO.getPages())
                                        .price(bookDTO.getPrice()).
                build();
        Optional<Book> optional = bookRepository.findByBookName(bookDTO.getBookName());
        if(optional.isPresent()){
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

}
