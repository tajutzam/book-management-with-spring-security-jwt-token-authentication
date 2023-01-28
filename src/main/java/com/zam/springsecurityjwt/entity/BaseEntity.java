package com.zam.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity <T> {
    @CreatedBy
    @Column(updatable = false  , nullable = false)
    protected T createdBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    // to create create date cant update set updatetable false
    @Column(updatable = false , nullable = false)
    protected Date createdDate;

    @LastModifiedBy
    protected T updatedBy;

    @LastModifiedDate
    protected Date updatedDate;

}
