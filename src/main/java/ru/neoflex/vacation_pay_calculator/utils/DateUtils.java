package ru.neoflex.vacation_pay_calculator.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DateUtils {
    private static final int YEAR_MOCK = 2000;
    private static final List<LocalDate> HOLIDAYS = List.of(
            LocalDate.of(YEAR_MOCK, 1,1),
            LocalDate.of(YEAR_MOCK, 1,2),
            LocalDate.of(YEAR_MOCK, 1,3),
            LocalDate.of(YEAR_MOCK, 1,4),
            LocalDate.of(YEAR_MOCK, 1,5),
            LocalDate.of(YEAR_MOCK, 1,6),
            LocalDate.of(YEAR_MOCK, 1,7),
            LocalDate.of(YEAR_MOCK, 1,8),
            LocalDate.of(YEAR_MOCK, 2,23),
            LocalDate.of(YEAR_MOCK, 3,8),
            LocalDate.of(YEAR_MOCK, 5,1),
            LocalDate.of(YEAR_MOCK, 5,9),
            LocalDate.of(YEAR_MOCK, 6,12),
            LocalDate.of(YEAR_MOCK, 11,4)
    );

    public static boolean isHoliday(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }
        for (LocalDate holidayDate : HOLIDAYS) {
            if (date.getDayOfMonth() == holidayDate.getDayOfMonth() && date.getMonth() == holidayDate.getMonth()) {
                return true;
            }
        }
        return false;
    }

    public static int excludeHolidays(LocalDate startDate, LocalDate endDate) {
        int restDays = endDate.compareTo(startDate);
        for (LocalDate date = startDate; date.isBefore(endDate) ; date = date.plusDays(1)) {
            if (DateUtils.isHoliday(date)) {
                restDays--;
            }
        }
        return restDays;
    }
}
