package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.ForgotPasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRequestRepository extends JpaRepository<ForgotPasswordRequest,Long> {
    Optional<ForgotPasswordRequest> findByEmployeeId(String employeeId);
}
