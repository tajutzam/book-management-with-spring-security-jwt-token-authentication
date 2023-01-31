package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.dto.BookDTO;
import com.zam.springsecurityjwt.dto.BookResponse;
import com.zam.springsecurityjwt.dto.BookUpdateDto;
import com.zam.springsecurityjwt.entity.Book;
import com.zam.springsecurityjwt.entity.Category;
import com.zam.springsecurityjwt.repo.BookRepository;
import com.zam.springsecurityjwt.repo.CategoryRepository;
import com.zam.springsecurityjwt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookServiceImpl implements BookService {
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
                            .author(book.getAuthor())
                            .urlImage(book.getCoverImage() == null ? null :  ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/api/v1/book/image/")
                                    .path(book.getId().toString())
                                    .toUriString())
                            .build()
            );
        });
        return response;
    }
    public Optional<Book> addBook(Book book){
            Optional<Book> optional = bookRepository.findByBookName(book.getBookName());
            if(optional.isPresent()){
                return Optional.empty();
            }
            return Optional.of(bookRepository.save(book));
        }
    public Optional<Book> findById(Integer uuid){
        return bookRepository.findById(uuid);
    }
    public Optional<Book> updateBook(BookUpdateDto bookUpdateDto){
        Optional<Book> optionalBook = findById(bookUpdateDto.getId());
        if(optionalBook.isEmpty()){
            return Optional.empty();
        }
        Optional<Category> optionalCategory = categoryRepository.findById(bookUpdateDto.getCategory());
        return optionalCategory.map(category -> bookRepository.save(
                Book.builder().id(bookUpdateDto.getId()).bookName(bookUpdateDto.getBookName())
                        .price(bookUpdateDto.getPrice())
                        .pages(bookUpdateDto.getPages())
                        .author(bookUpdateDto.getAuthor())
                        .category(category)
                        .coverImage(optionalBook.get().getCoverImage())
                        .build()));
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
