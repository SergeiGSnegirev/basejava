package com.urise.webapp;

import com.urise.webapp.util.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println("Date: " + date);
        System.out.println("Date getTime(): " + date.getTime());
        System.out.println("ms elapsed: " + (System.currentTimeMillis() - date.getTime()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println("Calendar (LA TZ) getTime(): " + calendar.getTime());

        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate: " + localDate);
        LocalTime localTime = LocalTime.now();
        System.out.println("LocalTime: " + localTime);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime: " + localDateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("SimpleDateFormat Date: " + sdf.format(date));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.println("DateTimeFormatter LocalDate: " + dtf.format(localDate));
        System.out.println("DateTimeFormatter LocalDate: " + localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        LocalDate startDate = DateUtil.of(2021, 4);
        System.out.println("startDate: " + startDate);
    }
}
