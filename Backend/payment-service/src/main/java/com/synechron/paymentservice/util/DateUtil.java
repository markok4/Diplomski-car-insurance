package com.synechron.paymentservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DATE_FORMAT = "dd.MM.yyyy"; // Define the date format

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateString);
    }
}
