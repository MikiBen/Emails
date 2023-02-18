package com.onwelo.emails.rest;

import com.onwelo.emails.model.Email;
import com.onwelo.emails.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/sendToAll")
    public void sendMail(@RequestBody Email email) {

        logger.info("send email to all users:" + email);
        emailSenderService.sendEmail(email);
    }

}
