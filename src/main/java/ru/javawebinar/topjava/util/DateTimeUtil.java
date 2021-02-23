package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetweenHalfOpen(Comparable<T> local, T start, T end) {
        return local.compareTo(start) >= 0 && local.compareTo(end) <= 0;
    }

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime == null ? LocalTime.MIN : startTime) >= 0
                && lt.compareTo(endTime == null ? LocalTime.MAX : endTime) < 0;
    }

    public static boolean isBetweenHalfOpen(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate == null ? LocalDate.MIN : startDate) >= 0 &&
                ld.compareTo(endDate == null ? LocalDate.MAX : endDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

