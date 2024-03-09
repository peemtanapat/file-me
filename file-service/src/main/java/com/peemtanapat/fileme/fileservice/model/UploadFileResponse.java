package com.peemtanapat.fileme.fileservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse extends BaseResponse {

    private String filename;

    public UploadFileResponse(String filename) {
        this.filename = filename;
    }
}
