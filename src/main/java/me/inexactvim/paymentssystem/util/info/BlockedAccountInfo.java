package me.inexactvim.paymentssystem.util.info;

import lombok.Getter;
import me.inexactvim.paymentssystem.util.NumberUtil;

@Getter
public class BlockedAccountInfo {

    private String holder;
    private String number;

    public BlockedAccountInfo(String name, String surname, long accountNumber) {
        holder = name + surname;
        number = NumberUtil.accountNumberFormat(accountNumber);
    }
}
