package me.inexactvim.paymentssystem.exception.user;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found");
    }
}
