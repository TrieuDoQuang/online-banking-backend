package com.example.onlinebankingapp.services.EmailServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("OTP for Transaction Verification");

        String emailBody = "Dear Customer,\n\n" +
                "TDK Banking has generated an OTP for your recent transaction request. Please use the OTP provided below to complete your transaction.\n\n" +
                "Your OTP is: " + otp + "\n\n" +
                "Important Information:\n" +
                "- Do not share this OTP with anyone.\n" +
                "- If you did not initiate this transaction, please contact our customer support immediately.\n\n" +
                "Thank you for choosing TDK Banking. We are committed to ensuring the security and privacy of your financial information.\n\n" +
                "Best regards,\n" +
                "TDK Banking Customer Support Team";

        message.setText(emailBody);
        mailSender.send(message);
    }
}
