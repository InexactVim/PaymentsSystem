package me.inexactvim.paymentssystem.object;

import lombok.Data;

import java.sql.Date;

@Data
public class CreditCard {

    private long id;
    private long number;
    private short code;
    private Date expirationDate;
    private long accountNumber;

}
