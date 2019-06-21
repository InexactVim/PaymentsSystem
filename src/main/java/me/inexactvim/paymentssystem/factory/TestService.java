package me.inexactvim.paymentssystem.factory;

import me.inexactvim.paymentssystem.object.User;
import me.inexactvim.paymentssystem.service.UserService;

public class TestService implements UserService {
    @Override
    public boolean isRegistered(String email) {
        return email.equalsIgnoreCase("me.clouder7@gmail.com");
    }

    @Override
    public boolean isPasswordValid(String email, String password) {
        return password.equalsIgnoreCase("qwe123");
    }

    @Override
    public User getUser(String email) {
        return new User();
    }
}
