package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.model.SendMailBody;
import org.springframework.http.ResponseEntity;

import java.io.File;

public interface IMailSenderAdapter {

    ResponseEntity<Object> notify(SendMailBody sendMailBody);
    ResponseEntity<Object> notifyUploadFileFail(String receiver, File file);
    ResponseEntity<Object> notifyUploadFileSuccess(String receiver, File file);
}
