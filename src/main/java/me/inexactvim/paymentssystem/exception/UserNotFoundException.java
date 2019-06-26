package me.inexactvim.paymentssystem.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found");
    }
}
