package com.modernfarmer.farmusspring.domain.myveggiegarden.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


@Component
public class DateManager {

    public String parsingDotDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
        return format.format(date);
    }


    public static String parsingDotDateTime(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
        return date.format(formatter);
    }

    public  String dotDateTime(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
        return date.format(formatter);
    }
    public int calculateDay(Date startDate, Date endDate) {
        long differenceMillis = endDate.getTime() - startDate.getTime();
        long differenceDays = differenceMillis / (1000 * 60 * 60 * 24);
        return (int) differenceDays;
    }

    public static String formatDate(LocalDateTime date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }


    public Date addDate(Date date, int addDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, addDate); // 3일을 더함
        return cal.getTime();
    }

    public Date formatMonthStringToDate(String month) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.parse(month);
    }

    public Date formatDayStringToDate(String day) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(day);
    }

    public String formatDayDateToString(Date day)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(day);
    }

}
