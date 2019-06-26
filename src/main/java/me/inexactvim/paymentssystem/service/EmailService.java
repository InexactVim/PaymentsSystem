package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.exception.EmailMessagingException;

public interface EmailService {

    void sendMail(String recipient, String topic, String message) throws EmailMessagingException;
}
