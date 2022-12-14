package com.tasc.productservice.controllers;

import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.models.ApiException;
import com.tasc.productservice.models.request.CategoryRequest;
import com.tasc.productservice.models.response.BaseResponse;
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

    @RequestMapping(method = RequestMethod.GET, path = "parent")
    public ResponseEntity<BaseResponse> findParentByChild_2(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
                                                            @RequestParam(name = "id", required = false) Long id) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(pageSize)
                .withId(id)
                .build();
        return createResponse(categoryService.findParentByChild_2(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "child")
    public ResponseEntity<BaseResponse> findChildByParent_2(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                            @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
                                                            @RequestParam(name = "id", required = false) Long id) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(pageSize)
                .withId(id)
                .build();
        return createResponse(categoryService.findChildByParent_2(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "with_view")
    public ResponseEntity<BaseResponse> findWithView(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(name = "id", required = false) Long id) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(pageSize)
                .withId(id)
                .build();
        return createResponse(categoryService.findAllWithView(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "with_view")
    public ResponseEntity<BaseResponse> findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(name = "id", required = false) Long id) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(pageSize)
                .withId(id)
                .build();
        return createResponse(categoryService.findAll(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable Long id) {
        return createResponse(categoryService.findCategoryById(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "parent/{id}")
    public ResponseEntity<BaseResponse> findParentByChild_1(@PathVariable Long id) {
        return createResponse(categoryService.findParentByChild_1(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "child/{id}")
    public ResponseEntity<BaseResponse> findChildByParent_1(@PathVariable Long id) {
        return createResponse(categoryService.findChildByParent_1(id));
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
