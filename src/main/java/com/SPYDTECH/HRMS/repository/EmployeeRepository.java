package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    Employee findByEmail(String email);

    Optional<Employee> findByEmployeeId(String employeeId);

    @Query("SELECT e.lastName FROM Employee e WHERE e.role = ?1")
    List<String> findLastNamesByRole(String role);



}
