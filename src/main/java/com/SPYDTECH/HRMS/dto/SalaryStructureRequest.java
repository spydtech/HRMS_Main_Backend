package com.SPYDTECH.HRMS.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SalaryStructureRequest {
    private LocalDate effectiveDate;
        private String employeeId;
        private Map<String, BigDecimal> salaryComponentsMonthly;
        private Map<String, BigDecimal> salaryComponentsYearly;
}
