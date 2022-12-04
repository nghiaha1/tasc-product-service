package com.tasc.productservice.controllers;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.models.request.CategoryRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/category")
public class CategoryController extends BaseController{
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> createCategory(@RequestBody CategoryRequest request) {
        return createResponse(categoryService.createCategory(request));
    }
}
