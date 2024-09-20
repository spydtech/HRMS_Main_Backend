package com.SPYDTECH.HRMS.service;


import com.SPYDTECH.HRMS.dto.LeaveAcceptOrDeclineDTO;
import com.SPYDTECH.HRMS.dto.LeaveRequestDTO;
import com.SPYDTECH.HRMS.entites.LeaveRequest;
import com.SPYDTECH.HRMS.entites.LeaveStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestService {

    ResponseEntity createALeaveRequest(LeaveRequestDTO leaveRequestDTO);

    ResponseEntity acceptOrDeclineOrCancel(LeaveAcceptOrDeclineDTO leaveAcceptOrDeclineDTO);

    ResponseEntity getAllPendingRequest();

    ResponseEntity getAllCurrentUserLeaveRequest();

    LeaveRequest createLeaveRequest(LeaveRequest leaveRequest);

   List<LeaveRequest> getLeaveStatusByHr(String employeeId);


    public LeaveRequest updateLeaveRequestByHR(String employeeId, LocalDate fromDate, LeaveStatus leaveStatus);
}
