package com.peemtanapat.fileme.filemeapigateway.model;

import lombok.Data;

@Data
public class SendMailBody {

    private String receiver;
    private String subject;
    private String body;
}
