package com.peemtanapat.fileme.fileservice.service;

import com.peemtanapat.fileme.fileservice.Constant;
import com.peemtanapat.fileme.fileservice.model.SendMailBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class MailSenderAdapter implements IMailSenderAdapter {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<Object> notify(SendMailBody sendMailBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SendMailBody> sendMailBodyEntity = new HttpEntity<>(sendMailBody, headers);

        try {
            URI uri = new URI(Constant.SEND_MAIL_URL);
            ResponseEntity<Object> response = restTemplate.postForEntity(uri, sendMailBodyEntity, Object.class);
            return response;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> notifyUploadFileFail(String receiver, String filename) {
        String body = Constant.UPLOAD_FAIL_BODY.replace("{{filename}}", filename);
        SendMailBody sendMailBody = new SendMailBody(receiver, Constant.UPLOAD_FAIL_SUBJECT, body);

        return notify(sendMailBody);
    }

    @Override
    public ResponseEntity<Object> notifyUploadFileSuccess(String receiver, String filename) {
        String body = Constant.UPLOAD_SUCCESS_BODY.replace("{{filename}}", filename);
        SendMailBody sendMailBody = new SendMailBody(receiver, Constant.UPLOAD_SUCCESS_SUBJECT, body);

        return notify(sendMailBody);
    }
}
