package com.SPYDTECH.HRMS.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BankAccountDto {
    private Long id;
    private String accountHolderName;
    private String accountNumber;
    private String bankName;
    private String city;
    private String branchName;
    private String ifscCode;

}
