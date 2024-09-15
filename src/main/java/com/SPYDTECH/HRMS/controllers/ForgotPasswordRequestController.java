package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.dto.ForgotPasswordRequestDto;
import com.SPYDTECH.HRMS.entites.ForgotPasswordRequest;
import com.SPYDTECH.HRMS.entites.RequestStatus;
import com.SPYDTECH.HRMS.service.ForgotPasswordRequestService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class ForgotPasswordRequestController {
    @Autowired
    ForgotPasswordRequestService forgotPasswordRequestService;
    @PostMapping("/forget-password")
    public ResponseEntity forgetPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) throws MessagingException {
        return ResponseEntity.ok(forgotPasswordRequestService.forgetPassword(forgotPasswordRequestDto));
    }

    @GetMapping("/getAllRequests")
    public ResponseEntity<List<ForgotPasswordRequest>> getAllEmployees(){
        return new ResponseEntity<>(forgotPasswordRequestService.getAllRequest(), HttpStatus.OK);
    }

    @PutMapping("/status-change")
    public ResponseEntity statusChange(@RequestParam String employeeId, @RequestParam RequestStatus requestStatus,@RequestParam(value = "newPassword", required = false) String newPassword) throws MessagingException {
        return ResponseEntity.ok(forgotPasswordRequestService.statusChange(employeeId,requestStatus,newPassword));
    }

}
