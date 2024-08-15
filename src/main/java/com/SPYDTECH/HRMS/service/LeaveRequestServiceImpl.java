package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.LeaveAcceptOrDeclineDTO;
import com.SPYDTECH.HRMS.dto.LeaveRequestDTO;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.LeaveRequest;
import com.SPYDTECH.HRMS.entites.LeaveStatus;
import com.SPYDTECH.HRMS.entites.User;
import com.SPYDTECH.HRMS.exceptions.ErrorResponse;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import com.SPYDTECH.HRMS.repository.LeaveRequestRepository;
import com.SPYDTECH.HRMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    @Override
    public ResponseEntity createALeaveRequest(LeaveRequestDTO leaveRequestDTO) {
        String username = getUsername();
        Employee requestRaisedUser;
        LeaveRequest leaveRequestDetails;

        if (username != null && !username.isEmpty()) {
            if (employeeRepository.existsByEmail(username)) {
                requestRaisedUser = employeeRepository.findByEmail(username);
            } else {
                ErrorResponse errorResponse = new ErrorResponse("Employee not exists.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            ErrorResponse errorResponse = new ErrorResponse("User is not logged in or Input is null or empty.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
        }

        try {
            if (requestRaisedUser != null) {
                leaveRequestDetails = addLeaveRequest(leaveRequestDTO, requestRaisedUser);
                leaveRequestRepository.save(leaveRequestDetails);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse("LeaveModel Request Not Raised" + message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.ok("LeaveModel Request Raised Successfully");
    }

    @Override
    public ResponseEntity acceptOrDeclineOrCancel(LeaveAcceptOrDeclineDTO leaveAcceptOrDeclineDTO) {
        List<LeaveRequest> raisedRequestDetails = leaveRequestRepository.findByEmployeeId(leaveAcceptOrDeclineDTO.getEmployeeId());

        if (raisedRequestDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No leave requests found for the provided email.");
        }

        List<LeaveRequest> currentRequest = raisedRequestDetails.stream()
                .filter(e -> e.getEmployeeId().equals(leaveAcceptOrDeclineDTO.getEmployeeId()) &&
                        e.getFromDate().equals(leaveAcceptOrDeclineDTO.getFromDate()) &&
                        e.getEndDate().equals(leaveAcceptOrDeclineDTO.getEndDate()))
                .collect(Collectors.toList());

        if (currentRequest.size() == 1) {
            LeaveRequest leaveRequest = currentRequest.get(0);
            if (leaveAcceptOrDeclineDTO.getLeaveStatus().equals(LeaveStatus.ACCEPT)) {
                leaveRequest.setLeaveStatus(leaveAcceptOrDeclineDTO.getLeaveStatus());
               // leaveRequest.setAcceptOrRejectDate(LocalDateTime.parse(now.format(formatter)));
                leaveRequestRepository.save(leaveRequest);
                return ResponseEntity.ok("LeaveModel Accepted and status updated successfully.");
            }
            else{
                leaveRequest.setLeaveStatus(leaveAcceptOrDeclineDTO.getLeaveStatus());
               // leaveRequest.setRejectReason(leaveAcceptOrDeclineDTO.getRejectReason());
               // leaveRequest.setAcceptOrRejectDate(LocalDateTime.parse(now.format(formatter)));
                leaveRequestRepository.save(leaveRequest);
                return ResponseEntity.ok("LeaveModel Rejected and status and rejectReason updated successfully.");

            }

        } else if (currentRequest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching leave request found.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Multiple matching leave requests found.");
        }
    }

    @Override
    public ResponseEntity getAllPendingRequest() {

        List<LeaveRequest> pendingRequest = leaveRequestRepository.findAllByLeaveStatus(LeaveStatus.PENDING);

        return ResponseEntity.ok( pendingRequest != null ? pendingRequest : null);
    }

    @Override
    public ResponseEntity getAllCurrentUserLeaveRequest() {

        String emp =  getUsername();
        Employee getCurrent = employeeRepository.findByEmail(emp);
        List<LeaveRequest> AllRequestCurrentUser = new ArrayList<>();

        if(getCurrent == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user logged In");

        }
        AllRequestCurrentUser = leaveRequestRepository.findByEmployeeId(getCurrent.getEmployeeId());

        return ResponseEntity.ok(AllRequestCurrentUser);
    }

    @Override
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getLeaveStatusByHr(String employeeId) {
        List<LeaveRequest> leaveRequest=leaveRequestRepository.findByEmployeeId(employeeId);
        return leaveRequest;
    }

    @Override
    public LeaveRequest updateLeaveRequestByHR(String employeeId,int id,LeaveStatus leaveStatus) {
        LeaveRequest leaveRequest1=new LeaveRequest();
        List<LeaveRequest> leaveRequestList=leaveRequestRepository.findByEmployeeId(employeeId);
        for(LeaveRequest leaveRequest:leaveRequestList){
            if(leaveRequest.getId()==id){
                leaveRequest1.setLeaveStatus(leaveStatus);
                leaveRequest1.setId(id);
                leaveRequest1.setLeaveType(leaveRequest.getLeaveType());
                leaveRequest1.setEmployeeId(leaveRequest.getEmployeeId());
                leaveRequest1.setFromDate(leaveRequest.getFromDate());
                leaveRequest1.setEndDate(leaveRequest.getEndDate());
                leaveRequest1.setReason(leaveRequest.getReason());
                leaveRequest1.setName(leaveRequest.getName());
            }
        }

        return leaveRequestRepository.save(leaveRequest1);
    }


    private LeaveRequest addLeaveRequest(LeaveRequestDTO leaveRequestDTO, Employee requestRaisedUser) {
        LeaveRequest setDetails = new LeaveRequest();
        List<LeaveRequest> raisedRequests = leaveRequestRepository.findByEmployeeId(requestRaisedUser.getEmployeeId());

        List<LeaveRequest>  ValidRequest = raisedRequests.stream()
                .filter(e -> e.getEmployeeId().equals(requestRaisedUser.getEmployeeId()) &&
                        e.getFromDate().equals(leaveRequestDTO.getFromDate()) &&
                        e.getEndDate().equals(leaveRequestDTO.getEndDate()))
                .collect(Collectors.toList());


        if (!ValidRequest.isEmpty()) {
            leaveRequestRepository.deleteAll(ValidRequest);
        }

        setDetails.setName(requestRaisedUser.getFirstName()+" "+requestRaisedUser.getLastName());
        setDetails.setEmployeeId(requestRaisedUser.getEmployeeId());
        setDetails.setLeaveType(leaveRequestDTO.getLeaveType());
        setDetails.setFromDate(leaveRequestDTO.getFromDate());
        setDetails.setEndDate(leaveRequestDTO.getEndDate());
        setDetails.setReason(leaveRequestDTO.getLeaveReason());

        return setDetails;
    }


    public String getUsername() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = " ";
        }
        return username;

    }
}




