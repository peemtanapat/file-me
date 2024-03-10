package com.peemtanapat.fileme.filemeapigateway.model;

import lombok.Data;

@Data
public class FileMetadata {

    private String name;
    private String url;
    private String createdAt;
    private long size;

    public FileMetadata(String name) {
        this.name = name;
    }

    public FileMetadata(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public FileMetadata(String name, String url, long size) {
        this.name = name;
        this.url = url;
        this.size = size;
    }

    public FileMetadata(String name, String url, String createdAt, long size) {
        this.name = name;
        this.url = url;
        this.createdAt = createdAt;
        this.size = size;
    }
}
