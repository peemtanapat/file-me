package com.peemtanapat.fileme.fileservice.model;

import lombok.Data;

@Data
public class SendMailBody {

    private String receiver;
    private String subject;
    private String body;

    public SendMailBody(String receiver, String subject, String body) {
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }
}
