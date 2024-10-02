package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.SalaryStructureRequest;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.SalaryStructure;
import com.SPYDTECH.HRMS.exceptions.ErrorResponse;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import com.SPYDTECH.HRMS.repository.SalaryStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryStructureServiceImpl implements SalaryStructureService{
    @Autowired
    EmployeeRepository employeeRepository;
        @Autowired
        SalaryStructureRepository salaryStructureRepository;
        @Override
        public ResponseEntity saveSalary(SalaryStructureRequest salaryStructureDTO) {
            SalaryStructure salaryStructure = new SalaryStructure();
            Optional<Employee> employee = employeeRepository.findByEmployeeId(salaryStructureDTO.getEmployeeId());
            if (employee.isPresent()) {
                Optional<SalaryStructure> optional = salaryStructureRepository.findByEmployeeId(employee.get().getId());
                if (optional.isEmpty()) {
                    salaryStructure.setEmployee(employee.get());
                    salaryStructure.setEffectiveDate(salaryStructureDTO.getEffectiveDate());
                    Map<String, BigDecimal> yearlyComponents = salaryStructureDTO.getSalaryComponentsYearly();
                    salaryStructure.setSalaryComponentsYearly(yearlyComponents);
                    Map<String, BigDecimal> monthlyComponents = calculateMonthlyComponents(yearlyComponents);
                    salaryStructure.setSalaryComponentsMonthly(monthlyComponents);
                    salaryStructureRepository.save(salaryStructure);
                    return ResponseEntity.ok(convertEntityToDto(salaryStructure));
                } else {
                    salaryStructure = optional.get();
                    salaryStructure.setEmployee(employee.get());
                    salaryStructure.setEffectiveDate(salaryStructureDTO.getEffectiveDate());
                    Map<String, BigDecimal> yearlyComponents = salaryStructureDTO.getSalaryComponentsYearly();
                    salaryStructure.setSalaryComponentsYearly(yearlyComponents);
                    Map<String, BigDecimal> monthlyComponents = calculateMonthlyComponents(yearlyComponents);
                    salaryStructure.setSalaryComponentsMonthly(monthlyComponents);
                    salaryStructureRepository.save(salaryStructure);
                    return ResponseEntity.ok(convertEntityToDto(salaryStructure));
                }
            } else {
                ErrorResponse errorResponse = new ErrorResponse("Employee Id not found.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            }
        }

    @Override
        public ResponseEntity getSalary(String employeeId) {
            Optional<Employee> employee=employeeRepository.findByEmployeeId(employeeId);
            Optional<SalaryStructure> optional=salaryStructureRepository.findByEmployeeId(employee.get().getId());
            if(optional.isPresent()){
                SalaryStructure salaryStructure=optional.get();
                return ResponseEntity.ok(convertEntityToDto(salaryStructure));
            }else{
                ErrorResponse errorResponse = new ErrorResponse("Salary not found.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
            }
        }

        private Map<String, BigDecimal> calculateMonthlyComponents(Map<String, BigDecimal> yearlyComponents) {
            return yearlyComponents.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)
                    ));
        }


        public SalaryStructureRequest convertEntityToDto(SalaryStructure salaryStructure){
            SalaryStructureRequest salaryStructureRequest=new SalaryStructureRequest();
           salaryStructureRequest.setSalaryComponentsYearly(salaryStructure.getSalaryComponentsYearly());
           salaryStructureRequest.setSalaryComponentsMonthly(salaryStructure.getSalaryComponentsMonthly());
           salaryStructureRequest.setEffectiveDate(salaryStructure.getEffectiveDate());
           return salaryStructureRequest;
        }
}
