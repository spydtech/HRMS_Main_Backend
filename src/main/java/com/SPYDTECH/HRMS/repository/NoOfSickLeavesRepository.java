package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.NoOfSickLeaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoOfSickLeavesRepository extends JpaRepository<NoOfSickLeaves,Long> {
    Optional<NoOfSickLeaves> findByEmployeeId(String employeeId);
}
