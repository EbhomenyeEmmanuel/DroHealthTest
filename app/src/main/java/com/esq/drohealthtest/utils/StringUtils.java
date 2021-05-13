package com.esq.drohealthtest.utils;

public class StringUtils {

    public static String getNumberOfItemsToString(int number) {
        if (number < 1) {
            return String.format("%d %s", 0, "item(s)");
        } else {
            return String.format("%d %s", number, "item(s)");
        }
    }

    public static String getPriceNumberToString(int number) {
        if (number < 1) {
            return String.format("%s%d", "₦", 0);
        } else {
            return String.format("%s%d", "₦", number);
        }
    }
}
