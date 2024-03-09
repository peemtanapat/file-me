package com.peemtanapat.fileme.fileservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GetAllFilesResponse extends BaseResponse implements Serializable {

    private List<FileMetadata> files;

    public GetAllFilesResponse(List<FileMetadata> files) {
        this.files = files;
    }
}
