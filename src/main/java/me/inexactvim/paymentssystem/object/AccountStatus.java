package me.inexactvim.paymentssystem.object;

public enum AccountStatus {

    ACTIVE, BLOCKED;

    public static AccountStatus valueOf(byte ordinal) {
        return values()[ordinal];
    }
}
