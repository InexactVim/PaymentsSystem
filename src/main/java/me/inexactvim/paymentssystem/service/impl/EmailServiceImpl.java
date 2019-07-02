package me.inexactvim.paymentssystem.service.impl;

import me.inexactvim.paymentssystem.exception.EmailMessagingException;
import me.inexactvim.paymentssystem.service.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {

    private Session session;

    public EmailServiceImpl(Properties properties) {
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.password"));
            }
        });
    }

    @Override
    public void sendMail(String recipient, String topic, String message) throws EmailMessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setSentDate(new Date());
            try {
                mimeMessage.setSender(new InternetAddress(session.getProperty("mail.smtp.user"), session.getProperty("mail.smtp.sender")));
            } catch (UnsupportedEncodingException e) {
                mimeMessage.setSender(new InternetAddress(session.getProperty("mail.smtp.user")));
            }
            mimeMessage.setRecipients(Message.RecipientType.TO, recipient);
            mimeMessage.setSubject(topic, "utf-8");
            mimeMessage.setText(message, "utf-8");
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailMessagingException(e);
        }
    }
}
