package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.security.PBKDF2WithHmacSHA1PasswordEncryption;
import me.inexactvim.paymentssystem.service.*;
import me.inexactvim.paymentssystem.service.impl.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceFactory {

    private static UserService userService;
    private static EmailService emailService;
    private static AccountService accountService;
    private static PaymentService paymentService;
    private static CreditCardService creditCardService;

    static {
        userService = new UserServiceImpl(RepositoryFactory.getUserRepository(), PBKDF2WithHmacSHA1PasswordEncryption.getInstance());

        Properties emailProperties = new Properties();
        try (InputStream inputStream = SqlDatabaseFactory.class.getResourceAsStream("/email.properties")) {
            emailProperties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        emailService = new EmailServiceImpl(emailProperties);

        accountService = new AccountServiceImpl(RepositoryFactory.getAccountRepository());
        paymentService = new PaymentServiceImpl(RepositoryFactory.getPaymentRepository(), accountService);
        creditCardService = new CreditCardServiceImpl(RepositoryFactory.getCreditCardRepository());
    }

    public static synchronized UserService getUserService() {
        return userService;
    }

    public static synchronized EmailService getEmailService() {
        return emailService;
    }

    public static synchronized AccountService getAccountService() {
        return accountService;
    }

    public static synchronized PaymentService getPaymentService() {
        return paymentService;
    }

    public static synchronized CreditCardService getCreditCardService() {
        return creditCardService;
    }
}
