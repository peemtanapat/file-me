package com.peemtanapat.fileme.mailservice.controller;

import com.peemtanapat.fileme.mailservice.model.SendMailBody;
import com.peemtanapat.fileme.mailservice.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:8080" })
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @Autowired
    public MailSenderController(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping(value = "/mail", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> sendMail(@RequestBody SendMailBody sendMailBody) {
        mailSenderService.sendMail(sendMailBody.getReceiver(), sendMailBody.getSubject(), sendMailBody.getBody());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
