package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.model.SendMailBody;
import org.springframework.http.ResponseEntity;

public interface IMailSenderAdapter {

    ResponseEntity<Object> notify(SendMailBody sendMailBody);
    ResponseEntity<Object> notifyUploadFileFail(String receiver, String filename);
    ResponseEntity<Object> notifyUploadFileSuccess(String receiver, String filename);
}
