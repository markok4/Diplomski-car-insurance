package com.synechron.policycreationservice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final String DATE_FORMAT = "dd.MM.yyyy."; // Define the date format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static String dateToString(LocalDateTime date) {
        return date == null ? null : date.format(DATE_FORMATTER);
    }

    public static LocalDateTime stringToDate (String dateString) throws DateTimeParseException {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        } else {
            return LocalDate.parse(dateString, DATE_FORMATTER).atStartOfDay();
        }
    }
}