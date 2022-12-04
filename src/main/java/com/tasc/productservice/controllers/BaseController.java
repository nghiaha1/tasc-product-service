package com.tasc.productservice.controllers;

import com.tasc.productservice.models.request.ProductRequest;
import com.tasc.productservice.models.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public ResponseEntity createResponse(BaseResponse response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity createResponse(BaseResponse response, HttpStatus status) {
        return new ResponseEntity<>(response, status);
    }
}
