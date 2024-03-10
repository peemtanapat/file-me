package com.peemtanapat.fileme.filemeapigateway.service.feignservice;

import com.peemtanapat.fileme.filemeapigateway.config.FeignClientConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "file-service", path = "/files", configuration = FeignClientConfig.class)
public interface FileFeignClient {

    @GetMapping()
    Object listAllFiles(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Headers("Content-Type: multipart/form-data")
    Object uploadFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
                      @RequestPart("file") MultipartFile file);

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    Resource downloadFile(@RequestParam("token") String token,
                                                 @RequestParam("fileId") String fileId);

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    Object deleteFile(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
                      @RequestParam("fileId") String fileId);
}
