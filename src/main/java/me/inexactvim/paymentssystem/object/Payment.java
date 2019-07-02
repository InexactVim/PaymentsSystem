package me.inexactvim.paymentssystem.object;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Payment {

    private Long id;
    private long senderAccountNumber;
    private long recipientAccountNumber;
    private BigDecimal amount;
    private String comment;
    private Timestamp timestamp;

}
