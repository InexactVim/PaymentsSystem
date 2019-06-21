package me.inexactvim.paymentssystem.object;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Payment {

    private long id;
    private long senderAccountNumber;
    private long recipientAccountNumber;
    private BigDecimal amount;
    private PaymentResult result;
    private String comment;
    private Timestamp timestamp;

}
