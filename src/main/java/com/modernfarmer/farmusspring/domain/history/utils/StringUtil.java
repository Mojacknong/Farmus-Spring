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

    // input -> String(2024-08-05 01:14:24.332920)
    // output -> mm/dd hh:mm
    public static String getPostFormattedDate(String date) {
        return date.substring(5, 10) + " " + date.substring(11, 16);
    }

}
