package com.peemtanapat.fileme.fileservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.peemtanapat.fileme.fileservice.config.S3ClientConfig;
import com.peemtanapat.fileme.fileservice.model.BaseResponse;
import com.peemtanapat.fileme.fileservice.service.FileS3Service;
import com.peemtanapat.fileme.fileservice.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class FileServiceControllerTest {
    @InjectMocks
    FileServiceController fileServiceController;
    @Mock
    FileS3Service fileS3Service;
    @Mock
    JWTService jwtService;

    private AmazonS3 s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;

    private String token = "token";

    @BeforeEach
    public void init() {
        System.out.println("bucketName = " + bucketName);
        System.out.println("accessKey = " + accessKey);
        System.out.println("secretKey = " + secretKey);

        MockitoAnnotations.openMocks(this);

        S3ClientConfig s3ClientConfig = new S3ClientConfig(accessKey, secretKey);
        s3Client = s3ClientConfig.initS3Client();

    }

    @Test
    public void testListAllFiles() {
        // Arrange
        String username = "tanapat.pm@gmail.com";
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(fileS3Service.listFiles(username)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> result = fileServiceController.listAllFiles(token);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testUploadFile() {
        // Arrange
        String token = "token";
        String username = "tanapat.pm@gmail.com";
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);

        // Act
        ResponseEntity<BaseResponse> result = fileServiceController.uploadFile(token, file);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // Verify that the file was uploaded to S3
        List<S3ObjectSummary> objects = s3Client.listObjects(bucketName).getObjectSummaries();
        System.out.println("objects = " + objects);
        boolean fileExists = objects.stream().anyMatch(o -> o.getKey().equals(file.getOriginalFilename()));
        assertTrue(fileExists);
    }

    @Test
    public void testDownloadFile() throws Exception {
        // Arrange
        String token = "token";
        String username = "tanapat.pm@gmail.com";
        String fileId = "fs-peemtanapat-2024-feb.pdf";
        Resource mockResource = mock(Resource.class);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(fileS3Service.downloadFile(fileId, username)).thenReturn(mockResource);

        // Act
        ResponseEntity<Resource> result = fileServiceController.downloadFile(token, fileId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}