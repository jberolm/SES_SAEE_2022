package es.unex.asee.frojomar.asee_ses.utils;

//import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    //private static final String FORMAT_DATE="dd-MMM-yyyy,hh:mm aa";
    private static final String FORMAT_TIME = "HH:mm:ss:SSS";
    private static final String FORMAT_DATE = "d-MMM-yyyy,HH:mm:ss aa";
    private static final String FORMAT_DATE_2 = "yyyy-MM-dd'T'hh:mm";
    private static final String TAG = "DateUtils";


    public static String time2Date(long time) {
        Date date01 = new Date();
        date01.setTime(time);
        DateFormat formatter = new SimpleDateFormat(FORMAT_TIME, Locale.forLanguageTag("es"));
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+01"));
        return formatter.format(date01);
    }

    public static String DateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat(FORMAT_DATE, Locale.forLanguageTag("es"));
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+01"));
        return formatter.format(date);
    }

    public static Date StringToDate(String s) {
        //Log.i(TAG, "Pasando "+s+" a Date");
        DateFormat formatter = new SimpleDateFormat(FORMAT_DATE, Locale.forLanguageTag("es"));
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+01"));
        Date date = null;
        try {
            date = formatter.parse(s);
            //Log.i(TAG, "Convertida "+s+" a "+date.toString());
        } catch (ParseException e) {
            //Log.e(TAG, "ParseException convirtiendo String a Date");
            e.printStackTrace();
        }
        return date;
    }

    public static String DateToString2(Date date) {
        DateFormat formatter = new SimpleDateFormat(FORMAT_DATE_2, Locale.forLanguageTag("es"));
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+01"));
        String s = formatter.format(date);
        //Log.i(TAG, "Convertida "+date.toString()+" a "+s);
        return s;
    }

    public static Date StringToDate2(String s) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_2, Locale.forLanguageTag("es"));
        format.setTimeZone(TimeZone.getTimeZone("GMT+01"));
        Date parsed = null;
        try {
            parsed = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsed;
    }
}
