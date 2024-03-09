package com.peemtanapat.fileme.fileservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileResponse {

    private String filename;

    public UploadFileResponse(String filename) {
        this.filename = filename;
    }
}
