package com.SPYDTECH.HRMS.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ForgotPasswordRequestDto {
    private Long id;
    private String employeeName;
    private String employeeId;
    private String email;
}
