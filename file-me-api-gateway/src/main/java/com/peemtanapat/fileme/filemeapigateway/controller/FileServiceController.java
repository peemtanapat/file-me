package com.peemtanapat.fileme.filemeapigateway.controller;

import com.peemtanapat.fileme.filemeapigateway.service.feignservice.FileFeignClient;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/files")
public class FileServiceController {

    private final FileFeignClient fileFeignClient;

    @Autowired
    public FileServiceController(FileFeignClient fileFeignClient) {
        this.fileFeignClient = fileFeignClient;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listFiles(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        Object response = fileFeignClient.listAllFiles(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Headers("Content-Type: multipart/form-data")
    ResponseEntity<?> uploadFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestPart("file") MultipartFile file) {
        Object response = fileFeignClient.uploadFile(token, file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    };

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<Resource> downloadFile(@RequestParam("token") String token,
            @RequestParam("fileId") String fileId) {
        Resource response = fileFeignClient.downloadFile(token, fileId);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileId);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> deleteFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestParam("fileId") String fileId) {
        Object response = fileFeignClient.deleteFile(token, fileId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
