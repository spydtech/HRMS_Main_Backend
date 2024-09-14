package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    EmployeeRepository employeeRepository;

    public void sendOtpEmail(String toEmail, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("OTP Verification");
        helper.setText("Your OTP for registration is: " + otp);
        mailSender.send(message);
    }

    public void sendEmployeeID(String toEmail, String employeeId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Employee Registration Is Successfully Completed");
        helper.setText("Your Employee ID : " + employeeId);
        mailSender.send(message);
    }

    public void sendEmployeeIdAndPassword(String toEmail, String employeeId,String password ) throws MessagingException {
       Optional<Employee> optionalEmployee= employeeRepository.findByEmployeeId(employeeId);
       Employee  employee=optionalEmployee.get();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject("Welcome! Your HR Portal Access Has Been Set Up");
        String emailContent = "<p>Dear " + employee.getFirstName() + " " + employee.getLastName() + ",</p>" +
                "<p>We’re excited to inform you that your access to the HR Portal has been successfully set up. You can now log in to manage your personal information, benefits, and more.</p>" +
                "<p><b>Your Login Credentials:</b></p>" +
                "<p>Username: " + toEmail + "<br>" +
                "Temporary Password: " + password + "</p>" +
                "<p><b>How to Log In:</b></p>" +
                "<p><b>Enter Your Username and Temporary Password:</b> Use the credentials listed above.</p>" +
                "<p>Visit the HR Portal: <a href='http://13.234.49.187:5173/'>http://13.234.49.187:5173/</a></p>" +
                "<p>Change Your Password: After logging in, you will be prompted to change your temporary password. Please create a new, secure password that you’ll remember.</p>" +
                "<p>Update Your Profile: Once logged in, please review and update your profile information as needed.</p>" +
                "<p>If you have any questions or run into any issues, don’t hesitate to reach out to our HR team at hr@spydtech.com.</p>";

        helper.setText(emailContent, true);
        mailSender.send(message);

    }


}
