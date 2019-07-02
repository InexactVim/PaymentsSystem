package me.inexactvim.paymentssystem.util.info;

import lombok.Getter;
import me.inexactvim.paymentssystem.object.Payment;
import me.inexactvim.paymentssystem.util.NumberUtil;

import java.text.SimpleDateFormat;

@Getter
public class PaymentInfo {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private final static String INFO = "%s <b>%s</b>";
    private final static String TOTAL = "<span class=\"color-%s\">%s$</span>";

    private String date;
    private String info;
    private String comment;
    private String total;

    public PaymentInfo(long targetAccountNumber, Payment payment) {
        date = DATE_FORMAT.format(payment.getTimestamp());
        comment = payment.getComment();

        if (payment.getRecipientAccountNumber() == targetAccountNumber) {
            info = String.format(INFO, "From", NumberUtil.accountNumberFormat(payment.getSenderAccountNumber()));
            total = String.format(TOTAL, "green", NumberUtil.amountFormat(payment.getAmount()));
        } else {
            info = String.format(INFO, "To", NumberUtil.accountNumberFormat(payment.getRecipientAccountNumber()));
            total = String.format(TOTAL, "red", "-" + NumberUtil.amountFormat(payment.getAmount()));
        }
    }
}
