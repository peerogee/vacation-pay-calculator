package ru.neoflex.vacation_pay_calculator.utils;

import java.time.LocalDate;

public class CalculateParametersValidator {
    public static void validateAverageSalaryIsNotNullAndPositive(Integer averageSalary) {
        if (averageSalary == null) {
            throw new IllegalArgumentException("The 'averageSalary' parameter must be provided.");
        }
        if (averageSalary < 0) {
            throw new IllegalArgumentException("The value of the request parameter 'averageSalary' must be greater than 0.");
        }
    }

    public static void validateRestDaysIsNotNullAndPositive(Integer restDays) {
        if (restDays == null) {
            throw new IllegalArgumentException("The 'restDays' parameter must be provided.");
        }
        if (restDays < 0) {
            throw new IllegalArgumentException("The value of the request parameter 'restDays' must be greater than 0.");
        }
    }

    public static void validatePeriodBetweenDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("The request parameter 'startDate hasn't been provided.'");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("The request parameter 'endDate hasn't been provided.'");
        }
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("The value of the request parameter 'startDate' has to " +
                    "contain a date that is earlier than the date of the request parameter 'endDate'.");
        }
    }
}
