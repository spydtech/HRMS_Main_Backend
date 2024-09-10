package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.AadharProof;
import com.SPYDTECH.HRMS.entites.IdType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AadharProofRepository extends JpaRepository<AadharProof,Long> {
    List<AadharProof> findByEmployeeId(String employeeId);

    Optional<AadharProof> findByIdType(IdType idType);

    Optional<AadharProof> findByEmployeeIdAndIdType(String employeeId, IdType idType);

    void deleteByEmployeeId(String employeeId);

    void deleteByEmployeeIdAndIdType(String employeeId, IdType idType);
}
