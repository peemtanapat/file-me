package com.peemtanapat.fileme.fileservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.peemtanapat.fileme.fileservice.model.FileMetadata;
import com.peemtanapat.fileme.fileservice.model.exception.FileDownloadException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileS3Service implements IFileService {

    @Value("${enable.mail.upload.notification}")
    private boolean enableMailNotification;

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    private final MailSenderAdapter mailSenderAdapter;

    private final Path root = Paths.get("downloads");

    @Autowired
    public FileS3Service(AmazonS3 s3Client, MailSenderAdapter mailSenderAdapter) {
        log.debug("bucketName="  + bucketName);
        log.debug("enableMailNotification="  + enableMailNotification);
        this.s3Client = s3Client;
        this.mailSenderAdapter = mailSenderAdapter;
    }

    public void initDirectory() {
        try {
            if (!root.toFile().isDirectory()) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            log.error("initDirectory: " + e);
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
            log.error("initDirectory: " + e);
            throw new RuntimeException("Could not initialize directory for upload!");
        }
    }

    private String getObjectPath(String owner, String filename) {
        return Base64Util.encode(owner) + "/" + filename;
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, String owner) {
        boolean isFail = false;

        String filename = multipartFile.getOriginalFilename();
        Path localFilePath = initDirectory(owner).resolve(filename);
        File localFile = localFilePath.toFile();

        try (FileOutputStream fileOutputStream = new FileOutputStream(localFile)) {
            fileOutputStream.write(multipartFile.getBytes());

            String fullFilePath = getObjectPath(owner, filename);
            PutObjectRequest request = new PutObjectRequest(bucketName, fullFilePath, localFile);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(localFile.length());
            request.setMetadata(metadata);

            s3Client.putObject(request);
        } catch (FileNotFoundException e) {
            isFail = true;
            log.error("uploadFile: " + e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            isFail = true;
            log.error("uploadFile: " + e);
            throw new RuntimeException(e);
        } finally {
            if (enableMailNotification) {
                if (isFail) {
                    mailSenderAdapter.notifyUploadFileFail(owner, localFile);
                } else {
                    mailSenderAdapter.notifyUploadFileSuccess(owner, localFile);
                }
            }
        }

        localFile.delete();
    }

    @Override
    public Resource downloadFile(String fileId, String owner) throws IOException, FileDownloadException {
        Path localFilePath = initDirectory(owner).resolve(fileId);
        log.debug("localFilePath=" + localFilePath);

        String fullFilePath = getObjectPath(owner, fileId);
        S3Object object = s3Client.getObject(bucketName, fullFilePath);

        try (S3ObjectInputStream objectInputStream = object.getObjectContent()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(localFilePath.toFile())) {
                byte[] read_buf = new byte[1024];
                int read_len;
                while ((read_len = objectInputStream.read(read_buf)) > 0) {
                    fileOutputStream.write(read_buf, 0, read_len);
                }
            }

            Resource resource = new UrlResource(localFilePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                log.debug("download file success: " + fileId + " " + owner);
                return resource;
            } else {
                log.error("Download File Error: file not found! " + fileId + " " + owner);
                throw new FileDownloadException("Download File Error: file not found!");
            }
        }
    }

    @Override
    public List<FileMetadata> listFiles(String owner) {
        List<FileMetadata> files = new ArrayList<>();
        String ownerFolderName = Base64Util.encode(owner) + "/";
        ListObjectsV2Request listObjectsV2Request =
                new ListObjectsV2Request()
                        .withBucketName(bucketName)
                        .withPrefix(ownerFolderName);
        ListObjectsV2Result objectListing = s3Client.listObjectsV2(listObjectsV2Request);

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            String filename = objectSummary.getKey().replace(ownerFolderName, "");
            files.add(new FileMetadata(filename, objectSummary.getLastModified(), objectSummary.getSize()));
        }

        return files;
    }

    @Override
    public FileMetadata deleteFile(String fileId, String owner) {
        String objectPath = getObjectPath(owner, fileId);
        s3Client.deleteObject(bucketName, objectPath);

        return new FileMetadata(fileId);
    }
}
