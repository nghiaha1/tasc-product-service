package com.tasc.productservice.repository;

import com.tasc.productservice.models.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductRequest, Integer> {
    Optional<ProductRequest> findProductRequestByBarcode(String barcode);
}
