package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.DepartmentRequest;
import com.SPYDTECH.HRMS.entites.Department;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    ResponseEntity saveDepartment(DepartmentRequest departmentRequest);

    ResponseEntity updateDepartment(String depName, DepartmentRequest departmentRequest);

    boolean deleteDepartment(String depName);

    ResponseEntity getDepartments();

    List<String> getLastNamesByRole(String role);

    Optional<String> findDepartmentHeadByName(String departmentName);

    Optional<String> findDepartmentNameByHead(String departmentHead);

}
