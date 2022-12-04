package com.tasc.productservice.database.entity;

import com.tasc.productservice.database.entity.base.BaseEntity;
import com.tasc.productservice.database.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 20)
    private String barcode;

    private String description;

    private String content;

    private String image;

    private BigDecimal price;

    private int quantity;

    private Status status;
}
