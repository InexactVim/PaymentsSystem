package me.inexactvim.paymentssystem.util.display;

import lombok.Getter;
import me.inexactvim.paymentssystem.object.CreditCard;
import me.inexactvim.paymentssystem.util.NumberUtil;

import java.text.SimpleDateFormat;

@Getter
public class CreditCardDisplay {

    private static SimpleDateFormat expirationDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String number;
    private String expirationDate;

    public CreditCardDisplay(CreditCard creditCard) {
        number = NumberUtil.creditCardNumberFormat(creditCard.getNumber());
        expirationDate = expirationDateFormat.format(creditCard.getExpirationDate());
    }
}
