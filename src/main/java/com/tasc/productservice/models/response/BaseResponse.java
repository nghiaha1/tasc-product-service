package com.tasc.productservice.models.response;

import lombok.Data;

@Data
public class BaseResponse {
    private int code;
    private String message;

    public BaseResponse() {
        this.code = 1;
        this.message = "Success";
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
