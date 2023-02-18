package com.onwelo.emails.service;

import com.onwelo.emails.db.User;

import com.onwelo.emails.model.Email;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService {
    private JavaMailSender mailSender;

    private UserService userService;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void sendEmail(Email email) {
        List<User> users = userService.findAll();
        for (User user : users) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mikolajmailtestowy@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject(email.getSubject());
            message.setText(email.getMessage());
            mailSender.send(message);
        }
    }
}
