package com.peemtanapat.fileme.mailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfiguration {

    @Value("${spring.mail.host}")
    private String mailSenderHost;
    @Value("${spring.mail.port}")
    private String mailSenderPort;
    @Value("${spring.mail.username}")
    private String mailSenderUsername;
    @Value("${spring.mail.password}")
    private String mailSenderPassword;
    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailProtocol;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailAuthEnable;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailTlsEnable;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailSenderHost);
        mailSender.setPort(Integer.parseInt(mailSenderPort));
        mailSender.setUsername(mailSenderUsername);
        mailSender.setPassword(mailSenderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProtocol);
        props.put("mail.smtp.auth", mailAuthEnable);
        props.put("mail.smtp.starttls.enable", mailTlsEnable);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
