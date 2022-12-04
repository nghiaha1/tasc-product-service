package com.tasc.productservice.services;

import com.tasc.productservice.database.entity.Product;
import com.tasc.productservice.database.entity.enums.Status;
import com.tasc.productservice.database.repository.ProductRepository;
import com.tasc.productservice.models.ApiException;
import com.tasc.productservice.models.enums.ERROR;
import com.tasc.productservice.models.request.ProductRequest;
import com.tasc.productservice.models.response.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public BaseResponse findAll() {
        try {
            log.info("Querry thành công.");
            Iterable<Product> productList = productRepository.findAll();
            return new BaseResponse(productList);
        } catch (Exception e) {
            log.info("Querry thất bại.");
            return new BaseResponse(500, "Hệ thống đang quá tải, xin vui lòng thử lại sau");
        }
    }

    public BaseResponse createProduct(ProductRequest request) {

        validateRequestCreateException(request);

        Product product = Product.builder()
                .name(request.getName())
                .barcode(request.getBarcode())
                .image(request.getImage())
                .price(request.getPrice())
                .content(request.getContent())
                .description(request.getDescription())
                .status(Status.ACTIVE)
                .build();

        productRepository.save(product);
        log.info("Tạo mới thành công.");
        return new BaseResponse(product);
    }

    public BaseResponse updateProduct(ProductRequest request, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            validateRequestCreateException(request);

            product.setName(request.getName());
            product.setImage(request.getImage());
            product.setBarcode(request.getBarcode());
            product.setContent(request.getContent());
            product.setDescription(request.getDescription());
            product.setQuantity(request.getQuantity());
            product.setStatus(Status.ACTIVE);

            productRepository.save(product);
            return new BaseResponse(product);
        }
        return new BaseResponse(500, "SERVER ERROR");
    }

    public BaseResponse deleteProduct(Long id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                productRepository.delete(product);
                return new BaseResponse(1, "DELETE SUCCESS");
            }
            return new BaseResponse(404, "Product not found");
        } catch (Exception e) {
            return new BaseResponse(0, "SERVER ERROR");
        }
    }

    private void validateRequestCreateException(ProductRequest request) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findProductRequestByBarcode(request.getBarcode());
        if (optionalProduct.isPresent()) {
            log.info("Lỗi trùng barcode.");
            throw new ApiException(ERROR.BARCODE_EXCEPTION, "Barcode đã tồn tại.");
        }

        if (StringUtils.isBlank(request.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Tên sản phẩm không được null");
        }

        if (StringUtils.isBlank(request.getImage())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Ảnh sản phẩm không được null");
        }

        if (StringUtils.isBlank(request.getContent())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Ảnh sản phẩm không được null");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Ảnh sản phẩm không được null");
        }
    }
}
