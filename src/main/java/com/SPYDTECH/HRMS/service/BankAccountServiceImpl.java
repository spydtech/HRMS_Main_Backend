package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.BankAccountDto;
import com.SPYDTECH.HRMS.entites.BankAccount;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.exceptions.ErrorResponse;
import com.SPYDTECH.HRMS.repository.BankRepository;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    BankRepository bankAccountRepository;
        @Autowired
        EmployeeRepository employeeRepository;
        @Override
        public ResponseEntity saveBankAccount(BankAccountDto bankAccountDto) {
            BankAccount bankAccount=new BankAccount();
            Employee employeeId=getEmployee();
            if (employeeId==null) {
                ErrorResponse errorResponse = new ErrorResponse("Unauthorized");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByEmployeeId(employeeId.getId());
            if(bankAccountOptional.isPresent()){
               return updateBankAccount(bankAccountDto);
            }else {
                Optional<BankAccount> existingAccount = bankAccountRepository.findByAccountNumber(bankAccountDto.getAccountNumber());
                if (existingAccount.isPresent()) {
                    ErrorResponse errorResponse = new ErrorResponse("Account number already exists.");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
                }
                bankAccount.setEmployee(employeeId);
                bankAccount.setAccountHolderName(employeeId.getFirstName());
                bankAccount.setBankName(bankAccountDto.getBankName());
                bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
                bankAccount.setBranchName(bankAccountDto.getBranchName());
                bankAccount.setCity(bankAccountDto.getCity());
                bankAccount.setIfscCode(bankAccountDto.getIfscCode());
                bankAccountRepository.save(bankAccount);
                return ResponseEntity.ok(convertEntityToDto(bankAccount));
            }
        }



        @Override
        public ResponseEntity getBankAccount() {
            Employee employeeId=getEmployee();
            if (employeeId==null) {
                ErrorResponse errorResponse = new ErrorResponse("Unauthorized");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByEmployeeId(employeeId.getId());
            if (bankAccountOptional.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Bank account not found for the logged-in employee.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);        }
            return ResponseEntity.ok(convertEntityToDto(bankAccountOptional.get()));
        }

        @Override
        public ResponseEntity updateBankAccount(BankAccountDto bankAccountDto) {
            Employee loggedInEmployee = getEmployee();
            Optional<BankAccount> existingAccountOpt = bankAccountRepository.findByEmployeeId(loggedInEmployee.getId());
            if (existingAccountOpt.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Bank account not found for the logged-in employee.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            BankAccount existingAccount = existingAccountOpt.get();
            existingAccount.setAccountHolderName(loggedInEmployee.getFirstName());
            if(bankAccountDto.getBankName()!=null) {
                existingAccount.setBankName(bankAccountDto.getBankName());
            }
            if (bankAccountDto.getAccountNumber() != null && !bankAccountDto.getAccountNumber().equals(existingAccount.getAccountNumber())) {
                Optional<BankAccount> duplicateAccountOpt = bankAccountRepository.findByAccountNumber(bankAccountDto.getAccountNumber());
                if (duplicateAccountOpt.isPresent()) {
                    ErrorResponse errorResponse = new ErrorResponse("Account number already exists.");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
                }
            }
            existingAccount.setBankName(bankAccountDto.getBankName() != null ? bankAccountDto.getBankName() : "");
            existingAccount.setAccountNumber(bankAccountDto.getAccountNumber() != null ? bankAccountDto.getAccountNumber() : "");
            existingAccount.setBranchName(bankAccountDto.getBranchName() != null ? bankAccountDto.getBranchName() : "");
            existingAccount.setCity(bankAccountDto.getCity() != null ? bankAccountDto.getCity() : "");
            existingAccount.setIfscCode(bankAccountDto.getIfscCode() != null ? bankAccountDto.getIfscCode() : "");
            existingAccount.setEmployee(loggedInEmployee);
            bankAccountRepository.save(existingAccount);
            return ResponseEntity.ok(convertEntityToDto(existingAccount));
        }

        public Employee getEmployee() {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) principal;
            Employee employee=employeeRepository.findByEmail(email);
            return employee;
        }

        public BankAccountDto convertEntityToDto(BankAccount bankAccount){
            BankAccountDto bankAccountDto=new BankAccountDto();
            bankAccountDto.setId(bankAccount.getId());
            bankAccountDto.setAccountHolderName(bankAccount.getAccountHolderName());
            bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
            bankAccountDto.setBankName(bankAccount.getBankName());
            bankAccountDto.setCity(bankAccount.getCity());
            bankAccountDto.setBranchName(bankAccount.getBranchName());
            bankAccountDto.setIfscCode(bankAccount.getIfscCode());
            return bankAccountDto;
        }
}
