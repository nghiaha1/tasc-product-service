package com.tasc.productservice.models.enums;

public enum ERROR {
    SYSTEM_ERROR(99, "System Error"),
    SUCCESS(1, "Success"),
    INVALID_PARAM(10, "invalid params"),


    // dau 1xx -> workflow code

    WORKFLOW_INVALID(100, "workflow code invalid"),

    // dau 2xx -> stage


    STAGE_TYPE_INVALID(201, "stage type is invalid"),
    STAGE_INVALID(202, "stage invalid"),

    // dau 10xx
    BARCODE_EXCEPTION(1000, "barcode is existed")
    ;


    public int code;
    public String message;

    ERROR(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
