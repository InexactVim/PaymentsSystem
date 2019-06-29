package me.inexactvim.paymentssystem.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    private static SimpleDateFormat expirationDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public static Date parse(String source) throws ParseException {
        return new Date(expirationDateFormat.parse(source).getTime());
    }

    public static String format(Date date) {
        return expirationDateFormat.format(date);
    }
}
