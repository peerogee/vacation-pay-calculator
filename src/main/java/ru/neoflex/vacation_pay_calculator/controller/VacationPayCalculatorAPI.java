package ru.neoflex.vacation_pay_calculator.controller;

import java.time.LocalDate;

public interface VacationPayCalculatorAPI {
    String calculate(Integer averageSalary, Integer restDays, LocalDate startDate, LocalDate endDate);
}
