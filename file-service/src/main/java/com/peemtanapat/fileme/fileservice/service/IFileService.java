package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.model.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {

    void uploadFile(MultipartFile file, String owner);
    Resource downloadFile(String fileId, String owner);
    List<FileMetadata> getFiles(String owner);
    FileMetadata deleteFile(String fileId);
}
