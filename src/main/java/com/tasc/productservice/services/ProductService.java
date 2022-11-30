package com.tasc.productservice.services;

import com.tasc.productservice.models.request.ProductRequest;
import com.tasc.productservice.models.response.BaseResponse;
import com.tasc.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public BaseResponse createProduct(ProductRequest request) {
        return new BaseResponse();
    }

    public Optional<ProductRequest> findProductByBarcode(String barcode) {
        return productRepository.findProductRequestByBarcode(barcode);
    }

    public ProductRequest saveProduct(ProductRequest productRequest) {
        return productRepository.save(productRequest);
    }
}
