package com.tasc.productservice.models.request;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    @Column(length = 20)
    private String barcode;

    private String name;

    private String content;

    private String image;

    private BigDecimal price;

    private int quantity;

    private String description;
}
