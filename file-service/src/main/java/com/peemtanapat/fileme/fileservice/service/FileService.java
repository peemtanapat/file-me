package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.model.FileMetadata;
import com.peemtanapat.fileme.fileservice.util.DateTimeUtil;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService implements IFileService {

    private final Path root = Paths.get("uploads");

    public void initDirectory() {
        try {
            if (!root.toFile().isDirectory()) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize directory for upload!");
        }
    }

    public Path initDirectory(String owner) {
        initDirectory();
        try {
            String encodedOwner = Base64Util.encode(owner);
            Path ownerPath = root.resolve(encodedOwner);
            if (!ownerPath.toFile().isDirectory()) {
                Files.createDirectories(ownerPath);
            }
            return ownerPath;
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize directory for upload!");
        }
    }

    @Override
    public void uploadFile(MultipartFile file, String owner) {
        if (file == null) {
            throw new RuntimeException("MultipartFile is not present.");
        }

        Path ownerPath = initDirectory(owner);
        try {
            Files.copy(file.getInputStream(),
                    ownerPath.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException("Upload file error!");
        }
    }

    @Override
    public Resource downloadFile(String fileId, String owner) {
        Path ownerPath = initDirectory(owner);
        Path filePath = ownerPath.resolve(fileId);
        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Download File Error: File can not read");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error Download File: " + e.getMessage());
        }
    }

    @Override
    public List<FileMetadata> getFiles(String owner) {
        Path path = initDirectory(owner);
        File directory = path.toFile();
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        List<FileMetadata> fileMetadata = new ArrayList<>();
        for (File file : files) {
            long fileSizeKb = file.length();

            String createdTime;
            try {
                FileTime rawCreatedTime = (FileTime) Files.getAttribute(file.toPath(), "creationTime");
                createdTime = DateTimeUtil.formatDateTime(rawCreatedTime);
            } catch (IOException e) {
                throw new RuntimeException("Can't get createdTime from file: " + file.getName());
            }

            fileMetadata.add(new FileMetadata(file.getName(),
                    file.getAbsolutePath(),
                    createdTime,
                    fileSizeKb));
        }

        return fileMetadata;
    }

    @Override
    public FileMetadata deleteFile(String fileId, String owner) {
        Path path = initDirectory(owner);
        Path targetPath = path.resolve(fileId);

        try {
            File targetFile = targetPath.toFile();
            long fileSize = targetFile.length();
            Files.deleteIfExists(targetPath);
            return new FileMetadata(targetFile.getName(), fileSize);
        } catch (Exception e) {
            throw new RuntimeException("Error Delete File: " + e.getMessage());
        }
    }
}
