package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.configuration.JwtTokenProvider;
import com.SPYDTECH.HRMS.entites.AadharProof;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.PasswordChange;
import com.SPYDTECH.HRMS.exceptions.UserException;
import com.SPYDTECH.HRMS.repository.AadharProofRepository;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.SPYDTECH.HRMS.entites.IdType.*;
import static com.SPYDTECH.HRMS.entites.IdType.PASSPORT;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeActivityService employeeActivityService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AadharProofService aadharProofService;
    @Autowired
    private AadharProofRepository aadharProofRepository;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder, EmployeeActivityService employeeActivityService, JwtTokenProvider jwtTokenProvider) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeActivityService = employeeActivityService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String createUserId(Employee employees) throws MessagingException {
        if(employeeRepository.existsByEmail(employees.getEmail())){
            return "Email is already registered.";
        }

        String password = employees.getPassword();
        employees.setPassword(passwordEncoder.encode(password));

        employeeRepository.save(employees);
        emailService.sendEmployeeIdAndPassword(employees.getEmail(),employees.getEmployeeId(),password);

        return "EmployeeId and password are sent successfully";
    }

    @Override
    public void logInEmployee(Employee employees) {
        employeeActivityService.employeeLoggedIn(employees);

    }

    @Override
    public void logOutEmployee(String email) {
        employeeActivityService.employeeLoggedOut(email);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployeeProfileByJwt(String jwt) throws Exception {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null){
            throw new UserException("Employee not exist with email" + email);
        }
        return employee;
    }

    @Transactional
    @Override
    public Employee updateEmployee(String employeeId, Employee employeeDetails) throws IOException {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);

        if (!employeeOptional.isPresent()) {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }

        Employee employee = employeeOptional.get();

        // Update employee details with null checks
        if (employeeDetails.getFirstName() != null) employee.setFirstName(employeeDetails.getFirstName());
        if (employeeDetails.getLastName() != null) employee.setLastName(employeeDetails.getLastName());
        if (employeeDetails.getBloodGroup() != null) employee.setBloodGroup(employeeDetails.getBloodGroup());
        if (employeeDetails.getDesignation() != null) employee.setDesignation(employeeDetails.getDesignation());
        if (employeeDetails.getAadharCardNumber() != null) employee.setAadharCardNumber(employeeDetails.getAadharCardNumber());
        if (employeeDetails.getPanNumber() != null) employee.setPanNumber(employeeDetails.getPanNumber());
        if (employeeDetails.getDob() != null) employee.setDob(employeeDetails.getDob());
        if (employeeDetails.getPersonalEmail() != null) employee.setPersonalEmail(employeeDetails.getPersonalEmail());
        if (employeeDetails.getPassword() != null) employee.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));  // Assuming password encryption
        if (employeeDetails.getEmail() != null) employee.setEmail(employeeDetails.getEmail());
        if (employeeDetails.getPhoneNumber() != null) employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        if (employeeDetails.getJoinDate() != null) employee.setJoinDate(employeeDetails.getJoinDate());
        if (employeeDetails.getRole() != null) employee.setRole(employeeDetails.getRole());
        if (employeeDetails.getDrivingLicenseNumber() != null) employee.setDrivingLicenseNumber(employeeDetails.getDrivingLicenseNumber());
        if (employeeDetails.getPassportNumber() != null) employee.setPassportNumber(employeeDetails.getPassportNumber());
        if (employeeDetails.getAlternatePhoneNumber() !=null) employee.setAlternatePhoneNumber(employeeDetails.getAlternatePhoneNumber());

        // Check if AadharProof exists for the employee
        List<AadharProof> aadharProofList = aadharProofRepository.findByEmployeeId(employeeId);

        if (aadharProofList == null || aadharProofList.isEmpty()) {
            // Create new AadharProof records if none exist
            aadharProofService.createAadharDetails(AADHARCARD, employeeDetails.getAadharCardNumber(), employeeId);
            aadharProofService.createAadharDetails(PANCARD, employeeDetails.getPanNumber(), employeeId);
            aadharProofService.createAadharDetails(DRIVINGLICENSE, employeeDetails.getDrivingLicenseNumber(), employeeId);
            aadharProofService.createAadharDetails(PASSPORT, employeeDetails.getPassportNumber(), employeeId);
        } else {
            // Update existing AadharProof records
            List<AadharProof> updatedProofs = new ArrayList<>();
            for (AadharProof aadharProof : aadharProofList) {
                switch (aadharProof.getIdType()) {
                    case AADHARCARD:
                        aadharProof.setIdNumber(employeeDetails.getAadharCardNumber());
                        break;
                    case PANCARD:
                        aadharProof.setIdNumber(employeeDetails.getPanNumber());
                        break;
                    case DRIVINGLICENSE:
                        aadharProof.setIdNumber(employeeDetails.getDrivingLicenseNumber());
                        break;
                    case PASSPORT:
                        aadharProof.setIdNumber(employeeDetails.getPassportNumber());
                        break;
                    default:
                        throw new RuntimeException("Unknown ID Type: " + aadharProof.getIdType());
                }
                updatedProofs.add(aadharProof);
            }
            aadharProofRepository.saveAll(updatedProofs);
        }

        return employeeRepository.save(employee);
    }
    public boolean deleteEmployee(String employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);

        if (!employeeOptional.isPresent()) {
            return false;
        }

        employeeRepository.delete(employeeOptional.get());
        return true;
    }


    public String updatePassword(String email, PasswordChange passwordChange){
        Optional<Employee> employee= Optional.ofNullable(employeeRepository.findByEmail(email));
        if(employee.isPresent()){
            Employee employee1=employee.get();
            if(passwordChange.getNewPassword().equals(passwordChange.getConfirmPassword())){
                if(passwordEncoder.matches(passwordChange.getCurrentPassword(), employee1.getPassword())){
                    employee1.setPassword(passwordEncoder.encode(passwordChange.getNewPassword()));
                    employeeRepository.save(employee1);
                    return "password updated successfully";
                }else{
                    return "password not matches with the old password";
                }

            }else{
                return "new Password and confirm Password are not equal";
            }

        }else{
            return "user is not found";
        }


    }

    @Override
    public String updateImage(String employeeId, MultipartFile file) throws IOException {
        Optional<Employee> employeeOptional=employeeRepository.findByEmployeeId(employeeId);
        Employee employee=employeeOptional.get();
        employee.setImage(file.getBytes());
        employeeRepository.save(employee);
        return "uploaded successfully";
    }

}
