package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.ForgotPasswordRequestDto;
import com.SPYDTECH.HRMS.entites.RequestStatus;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface ForgotPasswordRequestService {
    ResponseEntity forgetPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) throws MessagingException;

    ResponseEntity getAllRequest();

    ResponseEntity statusChange(String employeeId, RequestStatus requestStatus,String newPassword) throws MessagingException;
}
