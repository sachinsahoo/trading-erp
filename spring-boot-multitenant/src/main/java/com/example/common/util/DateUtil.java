package com.example.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateUtil {

    public static LocalDateTime toLocalDateTime(Long timestamp) {

        if(timestamp == null) {
            return null;
        } else {
            return Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

    public static Long getEpochTime(LocalDateTime dateTime) {

        if(dateTime != null ) {
            return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        } else {
            return null;
        }

    }

    public static Long getLong(Month month, int dd, int year) {
        LocalDateTime dateTime = LocalDateTime.of(year , month,dd,00,00);
        return getEpochTime(dateTime);

    }

    public static LocalDateTime getLDT(Month month, int dd, int year) {
        LocalDateTime dateTime = LocalDateTime.of(year , month ,dd ,00,00);
        return dateTime;

    }

    public static LocalDateTime getLDTMMYYYY(int month,  int year) {
        LocalDateTime dateTime = LocalDateTime.of(year , Month.of(month) ,01 ,00,00);
        return dateTime;
    }

    public static List<String> getYearMonths(Long start, Long end) {
        LocalDateTime lStart = toLocalDateTime(start);
        LocalDateTime lEnd = toLocalDateTime(end);

        List<String> months = new ArrayList<>();
        for (LocalDateTime date = lStart; date.isBefore(lEnd); date = date.plusMonths(1)) {
            String month = "" + String.valueOf(date.getYear()) + "_" + String.valueOf(date.getMonthValue());
            months.add(month);
        }
        return months;
    }

    public static List<LocalDateTime> getDays(LocalDateTime start, LocalDateTime end) {
        List<LocalDateTime> dayList = new ArrayList<>();
        for (LocalDateTime date = start; date.isBefore(end); date = date.plusDays(1)) {
            dayList.add(date);
        }
        return dayList;
    }

    public static String getLabelfromYM(String yearMonth) {
        String[] ym = yearMonth.split("_");

        String month = Month.of(Integer.valueOf(ym[1])).getDisplayName(TextStyle.SHORT, Locale.ENGLISH );
        String label = month + " " + ym[0];
        return label;
    }


}
