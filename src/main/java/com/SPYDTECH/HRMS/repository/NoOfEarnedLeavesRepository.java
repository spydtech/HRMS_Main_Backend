package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.NoOfEarnedLeaves;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoOfEarnedLeavesRepository extends JpaRepository<NoOfEarnedLeaves,Long> {
    Optional<NoOfEarnedLeaves> findByEmployeeId(String employeeId);
}
