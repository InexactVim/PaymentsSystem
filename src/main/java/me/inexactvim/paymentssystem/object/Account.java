package me.inexactvim.paymentssystem.object;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private Long number;
    private BigDecimal balance;
    private AccountStatus status;

    public boolean isBlocked() {
        return status == AccountStatus.BLOCKED;
    }
}
