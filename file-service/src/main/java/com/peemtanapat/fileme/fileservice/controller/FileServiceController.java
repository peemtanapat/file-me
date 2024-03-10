package com.peemtanapat.fileme.fileservice.controller;

import com.peemtanapat.fileme.fileservice.Constant;
import com.peemtanapat.fileme.fileservice.model.*;
import com.peemtanapat.fileme.fileservice.model.exception.FileDownloadException;
import com.peemtanapat.fileme.fileservice.service.FileS3Service;
import com.peemtanapat.fileme.fileservice.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8080" })
@RequestMapping("/files")
public class FileServiceController {

    private final FileS3Service fileService;
    private final JWTService jwtService;

    @Autowired
    public FileServiceController(FileS3Service fileS3Service, JWTService jwtService) {
        this.fileService = fileS3Service;
        this.jwtService = jwtService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAllFiles(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(new BaseResponse(Constant.INVALID_TOKEN_MSG),
                    HttpStatus.UNAUTHORIZED);
        }

        List<FileMetadata> files = fileService.listFiles(username);

        return new ResponseEntity<>(new GetAllFilesResponse(files), HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BaseResponse> uploadFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestParam("file") MultipartFile file) {
        String username = jwtService.getUsernameFromToken(token);
//        String username = "tanapat.pm@gmail.com";
        if (username == null) {
            return new ResponseEntity<>(new BaseResponse(Constant.INVALID_TOKEN_MSG),
                    HttpStatus.UNAUTHORIZED);
        }

        fileService.uploadFile(file, username);

        return new ResponseEntity<>(new UploadFileResponse(file.getOriginalFilename()), HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestParam("token") String token,
            @RequestParam("fileId") String fileId) throws IOException, FileDownloadException {
        String username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Resource resource = fileService.downloadFile(fileId, username);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileId);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> deleteFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestParam("fileId") String fileId) {
        String username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(new BaseResponse(Constant.INVALID_TOKEN_MSG),
                    HttpStatus.UNAUTHORIZED);
        }

        FileMetadata fileMetadata = fileService.deleteFile(fileId, username);

        return new ResponseEntity<>(new GetFileResponse(fileMetadata), HttpStatus.OK);
    }
}
