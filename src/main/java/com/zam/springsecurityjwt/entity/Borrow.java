package com.zam.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "borrowed_book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Borrow extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User  user;
    private Integer total_price;

}
