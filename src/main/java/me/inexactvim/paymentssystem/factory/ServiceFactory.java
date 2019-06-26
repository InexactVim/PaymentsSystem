package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.security.PBKDF2WithHmacSHA1PasswordEncryption;
import me.inexactvim.paymentssystem.service.AccountService;
import me.inexactvim.paymentssystem.service.EmailService;
import me.inexactvim.paymentssystem.service.UserService;
import me.inexactvim.paymentssystem.service.impl.AccountServiceImpl;
import me.inexactvim.paymentssystem.service.impl.EmailServiceImpl;
import me.inexactvim.paymentssystem.service.impl.UserServiceImpl;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceFactory {

    private static UserService userService;
    private static EmailService emailService;
    private static AccountService accountService;

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

}
