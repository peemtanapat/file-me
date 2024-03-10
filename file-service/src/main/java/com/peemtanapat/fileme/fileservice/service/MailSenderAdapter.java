package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.Constant;
import com.peemtanapat.fileme.fileservice.model.SendMailBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Service
@Slf4j
public class MailSenderAdapter implements IMailSenderAdapter {

    @Value("${send.mail.service.url}")
    private String SEND_MAIL_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<Object> notify(SendMailBody sendMailBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SendMailBody> sendMailBodyEntity = new HttpEntity<>(sendMailBody, headers);

        try {
            URI uri = new URI(SEND_MAIL_URL);
            ResponseEntity<Object> response = restTemplate.postForEntity(uri, sendMailBodyEntity, Object.class);
            return response;
        } catch (URISyntaxException e) {
            log.error("mail notify: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> notifyUploadFileFail(String receiver, File file) {
        String body = Constant.UPLOAD_FAIL_BODY.replace("{{filename}}", file.getName())
                .replace("{{size}}", file.length() + "")
                .replace("{{time}}", new Date().toString());
        SendMailBody sendMailBody = new SendMailBody(receiver, Constant.UPLOAD_FAIL_SUBJECT, body);

        return notify(sendMailBody);
    }

    @Override
    public ResponseEntity<Object> notifyUploadFileSuccess(String receiver, File file) {
        String body = Constant.UPLOAD_SUCCESS_BODY.replace("{{filename}}", file.getName())
                .replace("{{size}}", file.length() + "")
                .replace("{{time}}", new Date().toString());
        SendMailBody sendMailBody = new SendMailBody(receiver, Constant.UPLOAD_SUCCESS_SUBJECT, body);

        return notify(sendMailBody);
    }
}
