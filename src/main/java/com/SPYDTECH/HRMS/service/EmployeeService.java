package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.PasswordChange;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    String createUserId(Employee employees) throws MessagingException;
     void logInEmployee(Employee employees);

     void logOutEmployee(String email);
     public List<Employee> getAllEmployees();
     Employee findEmployeeProfileByJwt(String jwt) throws Exception;

    Employee updateEmployee(String employeeId, Employee employeeDetails) throws IOException;

    boolean deleteEmployee(String employeeId);

    public String updatePassword(String email, PasswordChange passwordChange);

}
