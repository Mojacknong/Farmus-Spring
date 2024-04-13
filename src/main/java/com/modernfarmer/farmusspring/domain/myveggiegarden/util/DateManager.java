package com.modernfarmer.farmusspring.domain.myveggiegarden.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class DateManager {

    public String dateParsing(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(date);
    }
    public int dayBetween(Date startDate, Date endDate) {
        long differenceMillis = endDate.getTime() - startDate.getTime();
        long differenceDays = differenceMillis / (1000 * 60 * 60 * 24);
        return (int) differenceDays;
    }


}
