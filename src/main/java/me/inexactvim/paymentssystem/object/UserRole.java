package me.inexactvim.paymentssystem.object;

public enum UserRole {

    CLIENT, ADMIN;

    public static UserRole valueOf(byte ordinal) {
//        if (values().length >= ordinal) {
//            throw new IllegalArgumentException("Ordinal can not be higher than ordinal length");
//        }

        return values()[ordinal];
    }

}
