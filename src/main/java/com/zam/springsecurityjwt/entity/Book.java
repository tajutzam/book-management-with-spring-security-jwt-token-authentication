package com.zam.springsecurityjwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_tbl")
public class Book extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
    private String bookName;
    private Integer price;
    private Integer pages;
    @ManyToOne(cascade = CascadeType.ALL , optional = true )
    @JoinColumn(nullable = false , name = "category")
  @JsonBackReference
    private Category category;
}

