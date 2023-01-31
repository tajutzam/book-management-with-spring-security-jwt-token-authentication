package com.zam.springsecurityjwt.repo;

import com.zam.springsecurityjwt.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School , UUID> {
}
