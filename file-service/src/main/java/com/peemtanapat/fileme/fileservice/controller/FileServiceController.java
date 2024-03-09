package com.peemtanapat.fileme.fileservice.controller;

import com.peemtanapat.fileme.fileservice.Constant;
import com.peemtanapat.fileme.fileservice.model.*;
import com.peemtanapat.fileme.fileservice.service.FileService;
import com.peemtanapat.fileme.fileservice.service.MailSenderAdapter;
import com.peemtanapat.fileme.fileservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8080" })
@RequestMapping("/files")
public class FileServiceController {

    private final FileService fileService;
    private final MailSenderAdapter mailSenderAdapter;
    private JWTUtil jwtUtil = new JWTUtil();

    @Autowired
    public FileServiceController(FileService fileService, MailSenderAdapter mailSenderAdapter) {
        this.fileService = fileService;
        this.mailSenderAdapter = mailSenderAdapter;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> getAllFiles(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(new BaseResponse(Constant.INVALID_TOKEN_MSG),
                    HttpStatus.UNAUTHORIZED);
        }

        List<FileMetadata> files = fileService.getFiles(username);

        return new ResponseEntity<>(new GetAllFilesResponse(files), HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BaseResponse> uploadFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestParam("file") MultipartFile file) {
        String username = jwtUtil.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(new BaseResponse(Constant.INVALID_TOKEN_MSG),
                    HttpStatus.UNAUTHORIZED);
        }

        fileService.uploadFile(file, username);

        mailSenderAdapter.notifyUploadFileSuccess(username, file.getOriginalFilename());

        return new ResponseEntity<>(new UploadFileResponse(file.getOriginalFilename()), HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestParam("token") String token,
            @RequestParam("fileId") String fileId) {
        String username = jwtUtil.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Resource resource = fileService.downloadFile(fileId, Constant.CURRENT_OWNER);

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
        String username = jwtUtil.getUsernameFromToken(token);
        if (username == null) {
            return new ResponseEntity<>(new BaseResponse(Constant.INVALID_TOKEN_MSG),
                    HttpStatus.UNAUTHORIZED);
        }

        FileMetadata fileMetadata = fileService.deleteFile(fileId, username);

        return new ResponseEntity<>(new GetFileResponse(fileMetadata), HttpStatus.OK);
    }
}
