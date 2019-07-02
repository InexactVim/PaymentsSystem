package me.inexactvim.paymentssystem.util.info;

import lombok.Getter;
import me.inexactvim.paymentssystem.object.Account;
import me.inexactvim.paymentssystem.util.NumberUtil;

@Getter
public class AccountInfo {

    private String number;
    private String balance;

    public AccountInfo(Account account) {
        number = NumberUtil.accountNumberFormat(account.getNumber());
        balance = NumberUtil.amountFormat(account.getBalance());
    }
}
