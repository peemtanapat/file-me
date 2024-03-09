package com.peemtanapat.fileme.fileservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFileResponse extends BaseResponse {

    private FileMetadata file;

    public GetFileResponse(FileMetadata file) {
        this.file = file;
    }
}
