package com.example.onlinebankingapp.services.EmailServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending emails, specifically OTP (One-Time Password) emails for transaction verification.
 * This class uses Spring's JavaMailSender to send emails.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail; // Email address from which the email will be sent, configured in application properties

    /**
     * Sends an OTP email to the specified recipient.
     *
     * @param toEmail The recipient's email address.
     * @param otp The OTP to be sent in the email.
     */
    //in charge: dat
    public void sendOtpEmail(String toEmail, String otp) {
        // Create a simple mail message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); // Set the sender's email address
        message.setTo(toEmail); // Set the recipient's email address
        message.setSubject("OTP for Transaction Verification"); // Set the email subject

        // Construct the email body
        String emailBody = "Dear Customer,\n\n" +
                "TDK Banking has generated an OTP for your recent transaction request. Please use the OTP provided below to complete your transaction.\n\n" +
                "Your OTP is: " + otp + "\n\n" +
                "Important Information:\n" +
                "- Do not share this OTP with anyone.\n" +
                "- If you did not initiate this transaction, please contact our customer support immediately.\n\n" +
                "Thank you for choosing TDK Banking. We are committed to ensuring the security and privacy of your financial information.\n\n" +
                "Best regards,\n" +
                "TDK Banking Customer Support Team";

        // Set the email body
        message.setText(emailBody);
        // Send the email
        mailSender.send(message);
    }
}
