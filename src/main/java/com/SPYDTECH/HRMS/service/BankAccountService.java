package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.BankAccountDto;
import org.springframework.http.ResponseEntity;

public interface BankAccountService {
    ResponseEntity updateBankAccount(BankAccountDto bankAccountDto);

    ResponseEntity saveBankAccount(BankAccountDto bankAccountDto);

    ResponseEntity getBankAccount();
}
