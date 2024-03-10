package com.peemtanapat.fileme.filemeapigateway.service.feignservice;

import com.peemtanapat.fileme.filemeapigateway.config.FeignClientConfig;
import com.peemtanapat.fileme.filemeapigateway.model.SendMailBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mail-service", configuration = FeignClientConfig.class)
public interface MailFeignClient {

    @PostMapping(value = "/mail", consumes = { MediaType.APPLICATION_JSON_VALUE })
    Object sendMail(@RequestBody SendMailBody sendMailBody);
}
