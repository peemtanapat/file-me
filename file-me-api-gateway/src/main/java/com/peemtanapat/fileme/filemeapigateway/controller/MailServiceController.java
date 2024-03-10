package com.peemtanapat.fileme.filemeapigateway.controller;

import com.peemtanapat.fileme.filemeapigateway.model.SendMailBody;
import com.peemtanapat.fileme.filemeapigateway.service.feignservice.MailFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/mail")
public class MailServiceController {

    private final MailFeignClient mailFeignClient;

    @Autowired
    public MailServiceController(MailFeignClient mailFeignClient) {
        this.mailFeignClient = mailFeignClient;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> sendMail(@RequestBody SendMailBody sendMailBody) {
        Object baseResponse = mailFeignClient.sendMail(sendMailBody);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
