package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.entites.AadharProof;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.IdType;
import com.SPYDTECH.HRMS.repository.AadharProofRepository;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AadharProofServiceImpl implements AadharProofService{

    @Autowired
    AadharProofRepository aadharProofRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public AadharProof createAadharDetails(IdType idType, String idNumber, String employeeId) throws IOException {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);
        Employee employee = employeeOptional.orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        switch (idType) {
            case AADHARCARD:
                employee.setAadharCardNumber(idNumber);
                break;
            case PANCARD:
                employee.setPanNumber(idNumber);
                break;
            case DRIVINGLICENSE:
                employee.setDrivingLicenseNumber(idNumber);
                break;
            case PASSPORT:
                employee.setPassportNumber(idNumber);
                break;
            default:
                throw new IllegalArgumentException("Unsupported ID type");
        }
        employeeRepository.save(employee);

        Optional<AadharProof> existingProofOptional = aadharProofRepository.findByEmployeeIdAndIdType(employeeId, idType);

        AadharProof aadharProof;
        if (existingProofOptional.isPresent()) {
            aadharProof = existingProofOptional.get();
            aadharProof.setIdNumber(idNumber);
        } else {
            aadharProof = new AadharProof();
            aadharProof.setEmployeeId(employeeId);
            aadharProof.setIdType(idType);
            aadharProof.setIdNumber(idNumber);
        }
        return aadharProofRepository.save(aadharProof);
    }

    @Override
    public AadharProof updateAadharDetails(IdType idType, String idNumber, String verified, String submitted
            , String employeeId ,MultipartFile file) throws IOException {
        // Optional<AadharProof> aadharProofOptional=aadharProofRepository.findByEmployeeId(employeeId);
        Optional<AadharProof> aadharProofOptional=aadharProofRepository.findByIdType(idType);
        AadharProof aadharProof1=aadharProofOptional.get();
        if(idType.name().equals("AADHARCARD")){
            aadharProof1.setIdNumber(idNumber);
        }else if(idType.name().equals("PANCARD")){
            aadharProof1.setIdNumber(idNumber);
        }else if(idType.name().equals("DRIVINGLICENSE")){
            aadharProof1.setIdNumber(idNumber);
        }else if(idType.name().equals("PASSPORT")){
            aadharProof1.setIdNumber(idNumber);
        }

        aadharProof1.setImage(file.getBytes());
        aadharProof1.setSubmitted(submitted);
        aadharProof1.setEmployeeId(employeeId);
        aadharProof1.setVerified(verified);
        aadharProof1.setIdType(idType);
        Optional<Employee> employeeOptional= employeeRepository.findByEmployeeId(employeeId);
        Employee employee1=employeeOptional.get();

        if(idType.name().equals("AADHARCARD")){
            employee1.setAadharCardNumber(idNumber);
        }else if(idType.name().equals("PANCARD")){
            employee1.setPanNumber(idNumber);
        }else if(idType.name().equals("DRIVINGLICENSE")){
            employee1.setDrivingLicenseNumber(idNumber);
        }else if(idType.name().equals("PASSPORT")){
            employee1.setPassportNumber(idNumber);
        }
        employeeRepository.save(employee1);

        return aadharProofRepository.save(aadharProof1);
    }

    @Override
    public List<AadharProof> getAadharDetailsByEmployeeId(String employeeId) {
        List<AadharProof> aadharProof=aadharProofRepository.findByEmployeeId(employeeId);
        return aadharProof;
    }
}
