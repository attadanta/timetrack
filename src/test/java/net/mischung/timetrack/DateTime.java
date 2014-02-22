package net.mischung.timetrack;

import java.util.Calendar;
import java.util.Date;

public class DateTime {

    public static Date date(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0);
    }
}
