package com.modernfarmer.farmusspring.domain.farmclub.util;

import java.time.LocalDateTime;

public class DateUtil {

    // LocalDateTime(2024-07-05 09:46:34.342303) -> String(yy.mm.dd hh:mm)
    public static String localDateTimeFormat(LocalDateTime date) {
        return date.getYear() % 100 + "." +
                date.getMonthValue() + "." +
                date.getDayOfMonth() + " " +
                date.getHour() + ":" +
                date.getMinute();
    }
}
