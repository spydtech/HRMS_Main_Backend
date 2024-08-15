package com.SPYDTECH.HRMS.dto;

import com.SPYDTECH.HRMS.entites.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LeaveAcceptOrDeclineDTO {

    private String email;

    private String employeeId;

    private LocalDate fromDate;

    private LocalDate endDate;

    private LeaveStatus leaveStatus;

    private String rejectReason;
}
