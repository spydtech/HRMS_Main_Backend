package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.SalaryStructureRequest;
import org.springframework.http.ResponseEntity;

public interface SalaryStructureService {
    ResponseEntity saveSalary(SalaryStructureRequest salaryStructureDTO);

    ResponseEntity getSalary(String employeeId);
}
