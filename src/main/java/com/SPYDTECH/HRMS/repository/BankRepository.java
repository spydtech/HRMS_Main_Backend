package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankAccount,Long> {
    Optional<BankAccount> findByEmployeeId(Long id);

    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
