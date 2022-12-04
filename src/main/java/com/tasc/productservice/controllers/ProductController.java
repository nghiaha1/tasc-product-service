package com.tasc.productservice.controllers;

import com.tasc.productservice.models.request.ProductRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(path = "/product")
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BaseResponse> findAll() {
        return createResponse(productService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> createProduct(@RequestBody ProductRequest request) {
        return createResponse(productService.createProduct(request));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody ProductRequest request,
                                                      @PathVariable long id) {
        return createResponse(productService.updateProduct(request, id));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable long id) {
        return createResponse(productService.deleteProduct(id));
    }
}
