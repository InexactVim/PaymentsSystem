package me.inexactvim.paymentssystem.util;

import me.inexactvim.paymentssystem.object.Payment;

import java.text.SimpleDateFormat;

public class PaymentDisplay {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private final static String INFO = "%s <b>%d</b>";
    private final static String TOTAL = "<span class=\"color-%s\">%s</span>";

    private String date;
    private String info;
    private String comment;
    private String total;

    public PaymentDisplay(long targetAccountNumber, Payment payment) {
        date = DATE_FORMAT.format(payment.getTimestamp());
        comment = payment.getComment();

        if (payment.getRecipientAccountNumber() == targetAccountNumber) {
            info = String.format(INFO, "From", payment.getSenderAccountNumber());
            total = String.format(TOTAL, "green", NumberUtil.amountFormat(payment.getAmount()));
        } else {
            info = String.format(INFO, "To", payment.getRecipientAccountNumber());
            total = String.format(TOTAL, "red", NumberUtil.amountFormat(payment.getAmount()));
        }
    }
}
