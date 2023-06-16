package com.paint23.projektpaint.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * This class enables to send email
 */

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static String EMAIL_SUBJECT_MESSAGE = "Confirm your email";
    private final static String SENDING_FAILED_MESSAGE = "failed to send email";
    private final static String EMAIL_SENDING_CONFIRMATION_EMAILS = "padfoot.lupus@gmail.com";// TODO
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender mailSender;

    public EmailService(){
        this.mailSender = new JavaMailSenderImpl();
    }

    @Override
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(EMAIL_SUBJECT_MESSAGE);
            helper.setFrom(EMAIL_SENDING_CONFIRMATION_EMAILS);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error(SENDING_FAILED_MESSAGE, e);
            throw new IllegalStateException(SENDING_FAILED_MESSAGE);
        }
    }
}
