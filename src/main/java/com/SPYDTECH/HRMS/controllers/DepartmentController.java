package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.dto.DepartmentRequest;
import com.SPYDTECH.HRMS.entites.Department;
import com.SPYDTECH.HRMS.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;


    @PostMapping("/create/department")
    public ResponseEntity saveDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.saveDepartment(departmentRequest));
    }

    @PutMapping("/edit/department/{depName}")
    public ResponseEntity updateDepartment(@PathVariable String depName, @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.updateDepartment(depName, departmentRequest));
    }

    @DeleteMapping("/delete/departmentName/{depName}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String depName) {
        boolean isRemoved = departmentService.deleteDepartment(depName);
        if (isRemoved) {
            return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Department not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("get/department")
    public ResponseEntity getDepartments() {
        return ResponseEntity.ok(departmentService.getDepartments());
    }

    @GetMapping("departmentHead/{role}")
    public ResponseEntity<?> getLastNamesByRole(@PathVariable("role") String role) {
        try {
            List<String> lastNames = departmentService.getLastNamesByRole(role);
            if (lastNames.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No employees found with the role: " + role);
            }
            return ResponseEntity.ok(lastNames);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/head/{departmentName}")
    public ResponseEntity<String> getDepartmentHeadByName(@PathVariable String departmentName) {
        try {
            Optional<String> departmentHead = departmentService.findDepartmentHeadByName(departmentName);
            return departmentHead.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body("Department head not found for: " + departmentName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the department head: " + e.getMessage());
        }
    }

    @GetMapping("/headName/{departmentHead}")
    public ResponseEntity<String> getDepartmentNameByHead(@PathVariable String departmentHead) {
        try {
            Optional<String> departmentName = departmentService.findDepartmentNameByHead(departmentHead);
            return departmentName
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(404).body("Department name not found for head: " + departmentHead));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the department name: " + e.getMessage());
        }
    }

}
