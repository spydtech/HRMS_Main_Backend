package com.SPYDTECH.HRMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {

    private String leaveType;

    private LocalDate fromDate;

    private LocalDate endDate;

    private String leaveReason;

    private String employeeId;
}
