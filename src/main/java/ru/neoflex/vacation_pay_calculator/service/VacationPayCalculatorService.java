package ru.neoflex.vacation_pay_calculator.service;

import java.time.LocalDate;

public interface VacationPayCalculatorService {
    String calculate(Integer averageSalary, Integer restDays);
    String calculate(Integer averageSalary, LocalDate startDate, LocalDate endDate);
}
