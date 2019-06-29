package me.inexactvim.paymentssystem.util.display;

import lombok.Getter;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.util.NumberUtil;

@Getter
public class AccountDisplay {

    private String number;
    private String balance;

    public AccountDisplay(Account account) {
        number = NumberUtil.accountNumberFormat(account.getNumber());
        balance = NumberUtil.amountFormat(account.getBalance());
    }
}
