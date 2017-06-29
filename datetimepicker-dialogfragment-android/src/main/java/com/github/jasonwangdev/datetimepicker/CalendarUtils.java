package com.github.jasonwangdev.datetimepicker;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public class CalendarUtils {

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static void setYear(Calendar calendar, int year) {
        calendar.set(Calendar.YEAR, year);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH);
    }

    public static void setMonth(Calendar calendar, int month) {
        calendar.set(Calendar.MONTH, month);
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static void setDay(Calendar calendar, int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    public static int getHour(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static void setHour(Calendar calendar, int hour) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    public static int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    public static void setMinute(Calendar calendar, int minute) {
        calendar.set(Calendar.MINUTE, minute);
    }

}
