package com.zam.springsecurityjwt.repo;

import com.zam.springsecurityjwt.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book , Integer> {

    Optional<Book> findByBookName(String bookName);
    Optional<Book> findById(Integer id);

}
