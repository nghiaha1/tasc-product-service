package com.tasc.productservice.database.repository;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.models.response.SearchCategoryResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryExtentRepository{
//    void searchCategory(Integer isRoot, String name, Integer page, Integer pageSize, SearchCategoryResponse.Data data);

    List<Category> findAll(SearchBody searchBody, SearchCategoryResponse.Data data);
}

