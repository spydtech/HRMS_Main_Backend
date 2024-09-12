package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.ForgotPasswordRequestDto;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.ForgotPasswordRequest;
import com.SPYDTECH.HRMS.entites.RequestStatus;
import com.SPYDTECH.HRMS.exceptions.ErrorResponse;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import com.SPYDTECH.HRMS.repository.ForgotPasswordRequestRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordRequestServiceImpl implements ForgotPasswordRequestService {
    @Autowired
    ForgotPasswordRequestRepository forgotPasswordRequestRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public ResponseEntity forgetPassword(ForgotPasswordRequestDto forgotPasswordRequestDto)  {
        ForgotPasswordRequest forgotPasswordRequest=new ForgotPasswordRequest();
        forgotPasswordRequest.setEmployeeId(forgotPasswordRequestDto.getEmployeeId());
        forgotPasswordRequest.setEmployeeName(forgotPasswordRequestDto.getEmployeeName());
        forgotPasswordRequest.setEmail(forgotPasswordRequestDto.getEmail());
        forgotPasswordRequest.setRequestStatus(RequestStatus.PENDING);
        forgotPasswordRequestRepository.save(forgotPasswordRequest);
        return ResponseEntity.ok(forgotPasswordRequest);
    }

    @Override
    public ResponseEntity statusChange(String employeeId,RequestStatus requestStatus, String newPassword) throws MessagingException {
        Optional<ForgotPasswordRequest> optional=forgotPasswordRequestRepository.findByEmployeeId(employeeId);
        if(optional.isPresent()){
            if(requestStatus.name().equals("APPROVED")) {
                ForgotPasswordRequest forgotPasswordRequest = optional.get();
                forgotPasswordRequest.setRequestStatus(requestStatus);
                forgotPasswordRequestRepository.save(forgotPasswordRequest);
               return confirmPasswordReset(forgotPasswordRequest.getEmployeeId(),newPassword);
            }else{
                ForgotPasswordRequest forgotPasswordRequest = optional.get();
                forgotPasswordRequest.setRequestStatus(requestStatus);
                forgotPasswordRequestRepository.save(forgotPasswordRequest);
                ErrorResponse errorResponse = new ErrorResponse("Request Rejected");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        }else{
            ErrorResponse errorResponse = new ErrorResponse("Employee Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    public ResponseEntity confirmPasswordReset(String employeeId, String newPassword) throws MessagingException {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee);
            sendNewPasswordEmail(employee.getEmail(), employee.getFirstName(), newPassword);
            return ResponseEntity.status(HttpStatus.OK).body("Email sent to Employee successfully");
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Employee Id not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    public void sendNewPasswordEmail(String toEmail, String employeeName, String newPassword) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Your New Password");
        String emailContent = "<p>Dear " + employeeName + ",</p>" +
                "<p>Your password has been reset. You can now log in with the following credentials:</p>" +
                "<p><b>Username:</b> " + toEmail + "<br>" +
                "<b>New Password:</b> " + newPassword + "</p>" +
                "<p>Please log in and change your password.</p>";
        helper.setText(emailContent, true);
        mailSender.send(message);
    }


    @Override
    public ResponseEntity getAllRequest() {
        List<ForgotPasswordRequest> list=forgotPasswordRequestRepository.findAll();
        return ResponseEntity.ok(list);
    }
}
