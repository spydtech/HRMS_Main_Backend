package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.NoOfCasualLeaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoOfCasualLeavesRepository extends JpaRepository<NoOfCasualLeaves,Long> {
    Optional<NoOfCasualLeaves> findByEmployeeId(String employeeId);
}
