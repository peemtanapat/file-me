package com.peemtanapat.fileme.fileservice.service;

import org.springframework.http.ResponseEntity;

public interface IMailSenderAdapter {

    ResponseEntity<Object> notifyUploadFileSuccess(String receiver, String filename);
}
