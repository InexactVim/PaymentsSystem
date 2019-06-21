package me.inexactvim.paymentssystem.security;

public interface PasswordEncryption {

    String encrypt(String password);

    boolean isValid(String target, String encryptedPassword);

}
