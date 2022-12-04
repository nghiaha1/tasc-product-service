package com.tasc.productservice.models.response;

import com.tasc.productservice.models.enums.ERROR;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public BaseResponse(T data) {
        this.code = 1;
        this.message = "Success";
        this.data = data;
    }

    public BaseResponse() {
        this.code = 1;
        this.message = "SUCCESS";
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(ERROR error, String message) {
        this.code = error.code;
        this.message = error.message;
    }

}
