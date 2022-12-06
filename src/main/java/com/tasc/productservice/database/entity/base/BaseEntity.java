package com.tasc.productservice.database.entity.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private Date createAt;
    @UpdateTimestamp
    private Date updateAt;
}
