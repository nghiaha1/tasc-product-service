package com.tasc.productservice.models;

import lombok.Data;

@Data
public class BasePagingData {

    private int currentPage;

    private int pageSize;

    private long totalItem;
}
