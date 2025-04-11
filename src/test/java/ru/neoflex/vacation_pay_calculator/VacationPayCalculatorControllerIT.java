package ru.neoflex.vacation_pay_calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VacationPayCalculatorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenSalaryAndRestDays_calculateReturnsValidResult() throws Exception {
        Integer averageSalary = 100000;
        Integer restDays = 14;
        String expectedResult = "47781,57";

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("restDays", restDays.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResult));
    }

    @Test
    void givenSalaryAndDates_calculateReturnsValidResult() throws Exception {
        Integer averageSalary = 100000;
        LocalDate startDate = LocalDate.of(2000, 7,1);
        LocalDate endDate = LocalDate.of(2000, 7,8);
        String expectedResult = "17064,85";

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedResult));
    }

    @Test
    void givenNegativeSalary_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        Integer averageSalary = -100000;
        Integer restDays = 14;

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("restDays", restDays.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(
                        "The value of the request parameter 'averageSalary' must be greater than 0."));
    }

    @Test
    void givenNegativeRestDays_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        Integer averageSalary = 100000;
        Integer restDays = -14;

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("restDays", restDays.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(
                        "The value of the request parameter 'restDays' must be greater than 0."));
    }

    @Test
    void givenEndDateEarlierThanStartDate_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        Integer averageSalary = 100000;
        LocalDate startDate = LocalDate.of(2000, 7,8);
        LocalDate endDate = LocalDate.of(2000, 7,1);

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("The value of the request parameter 'startDate' " +
                        "has to contain a date that is earlier than the date of the request parameter 'endDate'."));
    }

    @Test
    void givenNoParameters_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        mockMvc.perform(get("/calculate"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Required request parameter 'averageSalary' for method parameter type Integer is not present"));
    }

    @Test
    void givenSalary_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        Integer averageSalary = 100000;

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("The 'restDays' parameter must be provided."));
    }

    @Test
    void givenSalaryAndStartDate_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        Integer averageSalary = 100000;
        LocalDate startDate = LocalDate.of(2000, 7,1);

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("startDate", startDate.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("The 'restDays' parameter must be provided."));
    }

    @Test
    void givenSalaryAndEndDate_calculateReturnsAppropriateMessageAndBadRequestStatusCode() throws Exception {
        Integer averageSalary = 100000;
        LocalDate endDate = LocalDate.of(2000, 7,8);

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("The 'restDays' parameter must be provided."));
    }

    @Test
    void givenInvalidSalary_calculateReturnsAppropriateMessageAndInternalServerErrorStatusCode() throws Exception {
        String averageSalary = "aaaaa";
        Integer restDays = 14;

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary)
                        .param("restDays", restDays.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'")));
    }

    @Test
    void givenInvalidRestDays_calculateReturnsAppropriateMessageAndInternalServerErrorStatusCode() throws Exception {
        Integer averageSalary = 100000;
        String restDays = "aaaaa";

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("restDays", restDays))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'")));
    }

    @Test
    void givenInvalidStartDate_calculateReturnsAppropriateMessageAndInternalServerErrorStatusCode() throws Exception {
        Integer averageSalary = 100000;
        String startDate = "20.07.2000";
        LocalDate endDate = LocalDate.of(2000, 7,8);

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("startDate", startDate)
                        .param("endDate", endDate.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate'")));
    }

    @Test
    void givenInvalidEndDate_calculateReturnsAppropriateMessageAndInternalServerErrorStatusCode() throws Exception {
        Integer averageSalary = 100000;
        LocalDate startDate = LocalDate.of(2000, 7,28);
        String endDate = "20.07.2000";

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", averageSalary.toString())
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate'")));
    }
}