package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.model.FileMetadata;
import com.peemtanapat.fileme.fileservice.model.exception.FileDownloadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {

    void uploadFile(MultipartFile file, String owner);
    Resource downloadFile(String fileId, String owner) throws IOException, FileDownloadException;
    List<FileMetadata> listFiles(String owner);
    FileMetadata deleteFile(String fileId, String owner);
}
