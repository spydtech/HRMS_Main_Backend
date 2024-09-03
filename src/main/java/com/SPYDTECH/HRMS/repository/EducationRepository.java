package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.Education;
import com.SPYDTECH.HRMS.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    Optional<Education> findByEmployeeId(Long employeeId);
    Education findByEmployee(Employee employee);
    void deleteByEmployee(Employee employee);

}

