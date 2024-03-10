package com.peemtanapat.fileme.filemeapigateway.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetAllFilesResponse extends BaseResponse implements Serializable {

    private List<FileMetadata> files;

    public GetAllFilesResponse(List<FileMetadata> files) {
        this.files = files;
    }
}
