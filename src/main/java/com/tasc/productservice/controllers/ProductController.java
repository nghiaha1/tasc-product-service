package com.tasc.productservice.controllers;

import com.tasc.productservice.models.request.ProductRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/product")
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            Optional<ProductRequest> optionalProductRequest = productService.findProductByBarcode(productRequest.getBarcode());
            if (optionalProductRequest.isPresent()) {
                return createResponse(new BaseResponse(1000, ""));
            }
            if (productRequest.getProductName().isEmpty() || productRequest.getProductName() == null) {
                return createResponse(new BaseResponse(100, "Tên sản phẩm không được null"));
            }
            if (productRequest.getImage().isEmpty() || productRequest.getImage() == null) {
                return createResponse(new BaseResponse(101, "Ảnh sản phẩm không được null"));
            }
            if (productRequest.getContent().isEmpty() || productRequest.getContent() == null) {
                return createResponse(new BaseResponse(101, "Content sản phẩm không được null"));
            }
            if (productRequest.getDescription().isEmpty() || productRequest.getDescription() == null) {
                return createResponse(new BaseResponse(101, "Miêu tả sản phẩm không được null"));
            }
            productService.saveProduct(productRequest);
            return createResponse(productService.createProduct(productRequest));
        } catch (Exception e) {
            return createResponse(new BaseResponse(500, "Hệ thống đang quá tải, xin vui lòng thử lại sau"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
