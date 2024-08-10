package com.modernfarmer.farmusspring.domain.history.utils;

public class StringUtil {

    // input -> String(yyyy-mm-dd), String(yyyy-mm-dd)
    // output -> String(yy.mm.dd - yy.mm.dd)
    public static String getHistoryPeriod(String start, String end) {
        return getFormattedDate(start) + " - " + getFormattedDate(end);
    }

    // input -> String(yyyy-mm-dd)
    // output -> String(yy.mm.dd)
    public static String getFormattedDate(String date) {
        return date.substring(2, 10).replace("-", ".");
    }

}
