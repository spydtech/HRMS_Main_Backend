package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.dto.BankAccountDto;
import com.SPYDTECH.HRMS.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/account")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;
    @PostMapping
    public ResponseEntity saveBankAccount(@RequestBody BankAccountDto bankAccountDto){
        return ResponseEntity.ok(bankAccountService.saveBankAccount(bankAccountDto));
    }

    @PutMapping
    public ResponseEntity updateBankAccount(@RequestBody BankAccountDto bankAccountDto){
        return ResponseEntity.ok(bankAccountService.updateBankAccount(bankAccountDto));
    }

    @GetMapping
    public ResponseEntity getBankAccount() {
        return bankAccountService.getBankAccount();
    }
}
