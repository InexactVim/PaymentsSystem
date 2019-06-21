package me.inexactvim.paymentssystem.object;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private long number;
    private BigDecimal balance;
    private boolean blocked;

}
