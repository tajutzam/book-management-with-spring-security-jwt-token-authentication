package com.zam.springsecurityjwt.repo;

import com.zam.springsecurityjwt.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    Optional<Category> findByName(String name);
}
