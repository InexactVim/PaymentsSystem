package me.inexactvim.paymentssystem.object;

import lombok.Data;

@Data
public class User {

    private long id;
    private String name;
    private String surname;
    private UserRole role;
    private String email;
    private long accountNumber;
    private String password;

}
