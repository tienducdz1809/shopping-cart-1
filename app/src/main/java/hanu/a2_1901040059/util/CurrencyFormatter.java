package hanu.a2_1901040059.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    public static String format(Long num) {

        String LANGUAGE = "vi";
        String COUNTRY = "VN";

        return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
    }
}
