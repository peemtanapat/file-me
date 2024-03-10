package com.peemtanapat.fileme.filemeapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FileServiceFeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceFeignClientApplication.class, args);
    }

}
