package ru.neoflex.vacation_pay_calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.vacation_pay_calculator.utils.CalculateParametersValidator;
import ru.neoflex.vacation_pay_calculator.utils.DateUtils;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VacationPayCalculatorServiceImpl implements VacationPayCalculatorService {

    private static final float AVERAGE_DAYS_IN_MONTH = 29.3f;
    private static final String PAYMENT_FORMAT = "%.2f";

    @Override
    public String calculate(Integer averageSalary, Integer restDays) {
        CalculateParametersValidator.validateAverageSalaryIsNotNullAndPositive(averageSalary);
        CalculateParametersValidator.validateRestDaysIsNotNullAndPositive(restDays);
        float payment = averageSalary / AVERAGE_DAYS_IN_MONTH * restDays;
        return String.format(PAYMENT_FORMAT, payment);
    }

    @Override
    public String calculate(Integer averageSalary, LocalDate startDate, LocalDate endDate) {
        CalculateParametersValidator.validatePeriodBetweenDates(startDate, endDate);
        int daysWithoutHolidays = DateUtils.excludeHolidays(startDate, endDate);
        return calculate(averageSalary, daysWithoutHolidays);
    }
}
