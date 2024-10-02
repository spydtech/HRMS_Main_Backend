package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.dto.SalaryStructureRequest;
import com.SPYDTECH.HRMS.service.SalaryStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/salary/structure")
public class SalaryStructureController {
    @Autowired
    SalaryStructureService salaryStructureService;
        @PostMapping
        public ResponseEntity saveSalary(@RequestBody SalaryStructureRequest salaryStructureDTO){
            return ResponseEntity.ok(salaryStructureService.saveSalary(salaryStructureDTO));
        }

        @GetMapping
        public ResponseEntity getSalary(@RequestParam String employeeId ){
            return ResponseEntity.ok(salaryStructureService.getSalary(employeeId));
        }
}
