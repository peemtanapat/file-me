package com.peemtanapat.fileme.fileservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    private String message;

    public BaseResponse() {
    }

    public BaseResponse(String message) {
        this.message = message;
    }
}
