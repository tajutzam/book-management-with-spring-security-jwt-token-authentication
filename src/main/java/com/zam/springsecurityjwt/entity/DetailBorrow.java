package com.zam.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DetailBorrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id_id")
    private Book book_id;

    @ManyToOne
    @JoinColumn(name = "borrow_id_id")
    private Borrow borrow_id;

    @Column(name = "date_return")
    private Date dateReturn;

}
