package me.inexactvim.paymentssystem.object;

public enum UserRole {

    CLIENT, ADMIN;

    public static UserRole valueOf(byte ordinal) {
        return values()[ordinal];
    }

}
