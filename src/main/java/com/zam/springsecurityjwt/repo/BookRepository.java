package com.zam.springsecurityjwt.repo;

import com.zam.springsecurityjwt.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book , UUID> {

    Optional<Book> findByBookName(String bookName);

}
