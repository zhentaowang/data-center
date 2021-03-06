package com.adatafun.base.data.center.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tc on 2017/4/24.
 */
public class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH-mm-ss";

    public static Date stringToDate(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        Date parse = simpleDateFormat.parse(time);
        return parse;
    }

    public static Date stringToDate(String time,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date parse = simpleDateFormat.parse(time);
        return parse;
    }

    public static Date stringToDate(String time,SimpleDateFormat simpleDateFormat) throws ParseException {
        Date parse = simpleDateFormat.parse(time);
        return parse;
    }

    public static String dateToString(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static String dateToString(Date date,String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date getDate(String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return stringToDate(simpleDateFormat.format(new Date()),simpleDateFormat);
    }

    public static String getString(String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static Date completeToHSM(String time) throws ParseException {
        if (time.length() == "yyyy-MM-dd".length()) {
            return stringToDate(dateToString(stringToDate(time, "yyyy-MM-dd"), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
        } else if (time.length() == "yyyy-MM-dd HH:mm".length()) {
            return stringToDate(dateToString(stringToDate(time, "yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
        } else {
            return stringToDate(time, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static boolean verifyDate(String time,String format){
        boolean isSuccess = true;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(time);
        } catch (ParseException e) {
            isSuccess = false;
        }
        return isSuccess;
    }

    public static Date addDay(Date date, int day) {
        if (date == null) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    public static Date addHour(Date date, int hour) {
        if (date == null || hour <= 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        if (date == null || minute <= 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
    }

    public static Date formate(Date date, String format) throws ParseException {
        String s = dateToString(date, format);
        return stringToDate(s, format);
    }

}
