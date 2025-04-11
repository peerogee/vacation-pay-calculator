package ru.neoflex.vacation_pay_calculator.controller;

import lombok.RequiredArgsConstructor;
import ru.neoflex.vacation_pay_calculator.service.VacationPayCalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class VacationPayCalculatorController implements VacationPayCalculatorAPI {

    private final VacationPayCalculatorService service;

    @Override
    @GetMapping(path = "/calculate")
    public String calculate(
            @RequestParam Integer averageSalary,
            @RequestParam(required = false) Integer restDays,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return service.calculate(averageSalary, startDate, endDate);
        }
        return service.calculate(averageSalary, restDays);
    }
}
