package com.tasc.productservice.controllers;

import com.tasc.productservice.models.request.ProductRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
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
                log.info("Lỗi trùng barcode.");
                return createResponse(new BaseResponse(1000, ""));
            }
            if (productRequest.getProductName().isEmpty() || productRequest.getProductName() == null) {
                log.info("Lỗi thiếu tên sản phẩm.");
                return createResponse(new BaseResponse(100, "Tên sản phẩm không được null"));
            }
            if (productRequest.getImage().isEmpty() || productRequest.getImage() == null) {
                log.info("Lỗi thiếu ảnh sản phẩm.");
                return createResponse(new BaseResponse(101, "Ảnh sản phẩm không được null"));
            }
            if (productRequest.getContent().isEmpty() || productRequest.getContent() == null) {
                log.info("Lỗi thiếu content sản phẩm.");
                return createResponse(new BaseResponse(101, "Content sản phẩm không được null"));
            }
            if (productRequest.getDescription().isEmpty() || productRequest.getDescription() == null) {
                log.info("Lỗi thiếu mô tả sản phẩm.");
                return createResponse(new BaseResponse(101, "Miêu tả sản phẩm không được null"));
            }
            productService.saveProduct(productRequest);
            return createResponse(productService.createProduct(productRequest));
        } catch (Exception e) {
            log.info("Lỗi hệ thống.");
            return createResponse(new BaseResponse(500, "Hệ thống đang quá tải, xin vui lòng thử lại sau"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
