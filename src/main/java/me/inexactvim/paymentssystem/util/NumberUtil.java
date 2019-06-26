package me.inexactvim.paymentssystem.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {

    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.FLOOR);
    }

    public static String format(BigDecimal bigDecimal) {
        return (bigDecimal.compareTo(BigDecimal.ZERO) > 0 ? "+" : "") + DECIMAL_FORMAT.format(bigDecimal);
    }
}
