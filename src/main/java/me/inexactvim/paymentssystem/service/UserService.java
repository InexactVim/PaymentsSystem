package me.inexactvim.paymentssystem.service;

import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.repository.UserRepository;
import me.inexactvim.paymentssystem.security.PBKDF2WithHmacSHA1PasswordEncryption;
import me.inexactvim.paymentssystem.security.PasswordEncryption;

public interface UserService {

    /*private UserRepository repository;
    private PasswordEncryption passwordEncryption;

    public UserService(UserRepository repository) {
        this.repository = repository;
        passwordEncryption = PBKDF2WithHmacSHA1PasswordEncryption.getInstance();
    }

    public boolean userExists(String email) {
        return repository.exists(email);
    }*/

    boolean isRegistered(String email);

    boolean isPasswordValid(String email, String password);

    User getUser(String email);

}
