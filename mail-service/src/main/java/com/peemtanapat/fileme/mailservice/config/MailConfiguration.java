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

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(mailSenderHost);
//        mailSender.setPort(Integer.parseInt(mailSenderPort));
//
//        mailSender.setUsername(mailSenderUsername);
//        mailSender.setPassword(mailSenderPassword);

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("pm.tanapat@gmail.com");
        mailSender.setPassword("heux klff ntzl ruut");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
