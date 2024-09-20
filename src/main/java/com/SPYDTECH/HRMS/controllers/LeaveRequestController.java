package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.dto.LeaveAcceptOrDeclineDTO;
import com.SPYDTECH.HRMS.dto.LeaveRequestDTO;
import com.SPYDTECH.HRMS.entites.LeaveRequest;
import com.SPYDTECH.HRMS.entites.LeaveStatus;
import com.SPYDTECH.HRMS.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/addLeaveRequest")
    public ResponseEntity addLeave(@RequestBody LeaveRequestDTO leaveRequestDTO){
        return leaveRequestService.createALeaveRequest(leaveRequestDTO);
    }

    @PostMapping("/leaveRequestAction")
    public ResponseEntity leaveRequestAction(@RequestBody LeaveAcceptOrDeclineDTO leaveAcceptOrDeclineDTO){
        return leaveRequestService.acceptOrDeclineOrCancel(leaveAcceptOrDeclineDTO);
    }

    @GetMapping("/allPendingLeaveRequests")
    public ResponseEntity allLeaveRequests(){
        return leaveRequestService.getAllPendingRequest();
    }

    @GetMapping("/CurrentUserLeaveRequests")
    public ResponseEntity currentUserLeaveRequest(){
        return leaveRequestService.getAllCurrentUserLeaveRequest();
    }
//Hr will update this end point
    @PutMapping("/updateLeaveStatus")
    public ResponseEntity<LeaveRequest> updateLeaveRequestByHr(@RequestParam String employeeId, @RequestParam LocalDate fromDate, @RequestParam LeaveStatus leaveStatus){
        return new ResponseEntity<>(leaveRequestService.updateLeaveRequestByHR(employeeId, fromDate, leaveStatus), HttpStatus.OK);
    }

    @PostMapping("/leave")
    public ResponseEntity<LeaveRequest> createLeaveREquest(@RequestBody LeaveRequest leaveRequest){
        return new ResponseEntity<>(leaveRequestService.createLeaveRequest(leaveRequest),HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LeaveRequest>> getToKnowAboutLeaveStatus(@RequestParam String employeeId){
        return new ResponseEntity<>(leaveRequestService.getLeaveStatusByHr(employeeId),HttpStatus.OK);
    }



}
