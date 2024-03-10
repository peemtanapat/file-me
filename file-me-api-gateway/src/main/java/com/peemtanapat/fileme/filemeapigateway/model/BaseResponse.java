package com.peemtanapat.fileme.filemeapigateway.model;

import lombok.Data;

@Data
public class BaseResponse {

    private String message;

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        this.message = message;
    }
}
