package com.peemtanapat.fileme.fileservice.controller;

import com.peemtanapat.fileme.fileservice.Constant;
import com.peemtanapat.fileme.fileservice.model.FileMetadata;
import com.peemtanapat.fileme.fileservice.model.UploadFileResponse;
import com.peemtanapat.fileme.fileservice.service.FileService;
import com.peemtanapat.fileme.fileservice.service.MailSenderAdapter;
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

    @Autowired
    public FileServiceController(FileService fileService, MailSenderAdapter mailSenderAdapter) {
        this.fileService = fileService;
        this.mailSenderAdapter = mailSenderAdapter;
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<FileMetadata>> getAllFiles() {
        System.out.println(System.getenv("upload.path") + "${upload.path}");
        // TODO: Extract $email from JWT
        String extractEmail = Constant.CURRENT_OWNER;
        List<FileMetadata> files = fileService.getFiles(extractEmail);

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes =
            { MediaType.MULTIPART_FORM_DATA_VALUE, "multipart/form-data;charset=UTF-8" })
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        // TODO: Extract $email from JWT
        String extractEmail = Constant.CURRENT_OWNER;
        System.out.println("✅uploaded: " + file.getOriginalFilename());
        fileService.uploadFile(file, extractEmail);

        mailSenderAdapter.notifyUploadFileSuccess("tanapat.pm@gmail.com", file.getOriginalFilename());

        return new ResponseEntity<>(new UploadFileResponse(file.getOriginalFilename()), HttpStatus.OK);
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) //"application/octet-stream"
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileId") String fileId) {
        // TODO: Extract $email from JWT
        System.out.println("✅downloading fileId: " + fileId);
        Resource resource = fileService.downloadFile(fileId, Constant.CURRENT_OWNER);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileId);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<FileMetadata> deleteFile(@RequestParam("fileId") String fileId) {
        System.out.println("✅deleting fileId: " + fileId);

        FileMetadata fileMetadata = fileService.deleteFile(fileId);

        return new ResponseEntity<>(fileMetadata, HttpStatus.OK);
    }
}
