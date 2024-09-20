package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.LeaveAcceptOrDeclineDTO;
import com.SPYDTECH.HRMS.dto.LeaveRequestDTO;
import com.SPYDTECH.HRMS.entites.*;
import com.SPYDTECH.HRMS.exceptions.ErrorResponse;
import com.SPYDTECH.HRMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Autowired
    private NoOfEarnedLeavesRepository noOfEarnedLeavesRepository;

    @Autowired
    private NoOfCasualLeavesRepository noOfCasualLeavesRepository;

    @Autowired
    private NoOfSickLeavesRepository noOfSickLeavesRepository;

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
    public LeaveRequest updateLeaveRequestByHR(String employeeId,LocalDate fromDate, LeaveStatus leaveStatus) {

        LeaveRequest leaveRequest1 = new LeaveRequest();
        //List<LeaveRequest> leaveRequestList = leaveRequestRepository.findByEmployeeId(employeeId);

        LeaveRequest leaveRequest=leaveRequestRepository.findByEmployeeIdAndFromDate(employeeId,fromDate);
        LeaveType leaveType1 = null;
        long days=0;
       // LeaveRequest leaveRequest=leaveRequestList.get(0);



                leaveRequest1.setLeaveStatus(leaveStatus);

                leaveType1 = LeaveType.valueOf(leaveRequest.getLeaveType());
                leaveRequest1.setLeaveType(leaveRequest.getLeaveType());
                leaveRequest1.setEmployeeId(leaveRequest.getEmployeeId());
                leaveRequest1.setFromDate(leaveRequest.getFromDate());
                leaveRequest1.setEndDate(leaveRequest.getEndDate());
                days=ChronoUnit.DAYS.between(leaveRequest.getFromDate(),leaveRequest.getEndDate());
                leaveRequest1.setReason(leaveRequest.getReason());
                leaveRequest1.setName(leaveRequest.getName());
                leaveRequestRepository.save(leaveRequest1);



        // Find the employee by employeeId, check if present
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);
        if (!employeeOptional.isPresent()) {
            throw new NoSuchElementException("Employee not found with ID: " + employeeId);
        }
        Employee employee = employeeOptional.get();

        Optional<NoOfSickLeaves> noOfSickLeavesOptional = noOfSickLeavesRepository.findByEmployeeId(employeeId);
        NoOfSickLeaves noOfSickLeaves1;

        if (noOfSickLeavesOptional.isPresent()) {
            noOfSickLeaves1 = noOfSickLeavesOptional.get();
        } else {
            // Initialize with default values
            noOfSickLeaves1 = new NoOfSickLeaves();
            noOfSickLeaves1.setEmployeeId(employeeId);
            noOfSickLeaves1.setCreditedLeaves(10);  // Default credited leaves, adjust as needed
            noOfSickLeaves1.setRemainingLeaves(10);
            noOfSickLeaves1.setTakenLeaves(0);
            noOfSickLeaves1.setEmail(employee.getEmail());

            // Save the new record to the database
            noOfSickLeavesRepository.save(noOfSickLeaves1);
        }


        Optional<NoOfEarnedLeaves> noOfEarnedLeavesOptional = noOfEarnedLeavesRepository.findByEmployeeId(employeeId);
        NoOfEarnedLeaves noOfEarnedLeaves1;

        if (noOfEarnedLeavesOptional.isPresent()) {
            noOfEarnedLeaves1 = noOfEarnedLeavesOptional.get();
        } else {
            // Initialize with default values for earned leaves
            noOfEarnedLeaves1 = new NoOfEarnedLeaves();
            noOfEarnedLeaves1.setEmployeeId(employeeId);
            noOfEarnedLeaves1.setCreditedLeaves(10);  // Default credited leaves, adjust as needed
            noOfEarnedLeaves1.setRemainingLeaves(10);
            noOfEarnedLeaves1.setTakenLeaves(0);
            noOfEarnedLeaves1.setEmail(employee.getEmail());

            // Save the new record to the database
            noOfEarnedLeavesRepository.save(noOfEarnedLeaves1);
        }


        Optional<NoOfCasualLeaves> noOfCasualLeavesOptional = noOfCasualLeavesRepository.findByEmployeeId(employeeId);
        NoOfCasualLeaves noOfCasualLeaves1;

        if (noOfCasualLeavesOptional.isPresent()) {
            noOfCasualLeaves1 = noOfCasualLeavesOptional.get();
        } else {
            // Initialize with default values for casual leaves
            noOfCasualLeaves1 = new NoOfCasualLeaves();
            noOfCasualLeaves1.setEmployeeId(employeeId);
            noOfCasualLeaves1.setCreditedLeaves(10);  // Default credited leaves, adjust as needed
            noOfCasualLeaves1.setRemainingLeaves(10);
            noOfCasualLeaves1.setTakenLeaves(0);
            noOfCasualLeaves1.setEmail(employee.getEmail());

            // Save the new record to the database
            noOfCasualLeavesRepository.save(noOfCasualLeaves1);
        }


        // Handle leave status and leave type
        if (leaveStatus.name().equals("ACCEPT")) {
            if (leaveType1.name().equals("EARNEDLEAVE")) {
                NoOfEarnedLeaves noOfEarnedLeaves = new NoOfEarnedLeaves();
                noOfEarnedLeaves.setRemainingLeaves(noOfEarnedLeaves1.getCreditedLeaves() - days);
                noOfEarnedLeaves.setTakenLeaves(noOfEarnedLeaves1.getTakenLeaves() + days);
                noOfEarnedLeaves.setEmployeeId(employeeId);
                noOfEarnedLeaves.setEmail(employee.getEmail());
                noOfEarnedLeavesRepository.save(noOfEarnedLeaves);
            } else if (leaveType1.name().equals("CASUALLEAVE")) {
                NoOfCasualLeaves noOfCasualLeaves = new NoOfCasualLeaves();
                noOfCasualLeaves.setRemainingLeaves(noOfCasualLeaves1.getCreditedLeaves() - days);
                noOfCasualLeaves.setTakenLeaves(noOfCasualLeaves1.getTakenLeaves() + days);
                noOfCasualLeaves.setEmployeeId(employeeId);
                noOfCasualLeaves.setEmail(employee.getEmail());
                noOfCasualLeavesRepository.save(noOfCasualLeaves);
            } else if (leaveType1.name().equals("SICKLEAVE")) {
                NoOfSickLeaves noOfSickLeaves = new NoOfSickLeaves();
                noOfSickLeaves.setRemainingLeaves(noOfSickLeaves1.getCreditedLeaves() - 1);
                noOfSickLeaves.setTakenLeaves(noOfSickLeaves1.getTakenLeaves() + 1);
                noOfSickLeaves.setEmployeeId(employeeId);
                noOfSickLeaves.setEmail(employee.getEmail());
                noOfSickLeavesRepository.save(noOfSickLeaves);
            }
        }

        // Save the updated leave request
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




