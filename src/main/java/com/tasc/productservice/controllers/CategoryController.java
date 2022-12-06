package com.tasc.productservice.controllers;

import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.models.ApiException;
import com.tasc.productservice.models.request.CategoryRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.models.response.SearchCategoryResponse;
import com.tasc.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<BaseResponse> findAll(@RequestParam(name = "page", defaultValue = "1") int page,
//                                                @RequestParam(name = "limit", defaultValue = "10") int limit,
//                                                @RequestParam(name = "name", required = false) String name,
//                                                @RequestParam(name = "description", required = false) String description,
//                                                @RequestParam(name = "sort", required = false) String sort) {
//
//        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
//                .withPage(page)
//                .withLimit(limit)
//                .withName(name)
//                .withDescription(description)
//                .withSort(sort)
//                .build();
//        return createResponse(categoryService.findAllCategory(searchBody));
//    }

//    @RequestMapping(method = RequestMethod.GET)
//    public SearchCategoryResponse search(@RequestParam(name = "is_root", required = false) Integer isRoot,
//                                         @RequestParam(name = "name", required = false) String name,
//                                         @RequestParam(name = "page", required = false) Integer page,
//                                         @RequestParam(name = "page_size", required = false) Integer pageSize) {
//        return categoryService.search(isRoot, name, page, pageSize);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> search(@RequestParam(name = "is_root", defaultValue = "2") Integer isRoot,
                                               @RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                                               @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
                                               @RequestParam(name = "sort", required = false) String sort) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(pageSize)
                .withName(name)
                .withIsRoot(isRoot)
//                .withDescription(description)
                .withSort(sort)
                .build();
        return createResponse(categoryService.search(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable Long id) {
        return createResponse(categoryService.findCategoryById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> create(@RequestBody CategoryRequest request) {
        return createResponse(categoryService.createCategory(request));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<BaseResponse> edit(@PathVariable Long id,
                                             @RequestBody CategoryRequest request) {
        return createResponse(categoryService.editCategory(id, request));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) throws ApiException {
        return createResponse(categoryService.deleteCategory(id));
    }
}
