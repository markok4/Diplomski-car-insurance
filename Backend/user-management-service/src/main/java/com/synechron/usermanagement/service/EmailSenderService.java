package com.synechron.usermanagement.service;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

//    public void sendVerificationEmail(String email) throws MessagingException {
//        MimeMessage message = emailSender.createMimeMessage();
//        message.setFrom(new InternetAddress("synechronpraksa@gmail.com"));
//        message.setRecipients(MimeMessage.RecipientType.TO, email);
//        message.setSubject("Verification email");
//
//        String verificationUrl = "http://www.yourdomain.com/verify?token="; // Replace with your actual verification URL
//
//        String htmlContent = "<h1>Email Verification</h1>"
//                + "<p>Please click the link below to verify your email:</p>"
//                + "<a href=\"" + verificationUrl + "\">Verify Email</a>";
//        message.setContent(htmlContent, "text/html; charset=utf-8");
//        emailSender.send(message);
//    }
}
