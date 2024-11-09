package com.example.dynamicPdf.dataobjects.response;

public class BaseResponse {

    private final Integer statusCode;
    private final String message;

    public BaseResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}