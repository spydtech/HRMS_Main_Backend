package com.SPYDTECH.HRMS.service;


import com.SPYDTECH.HRMS.configuration.JwtTokenProvider;
import com.SPYDTECH.HRMS.dto.EducationDTO;
import com.SPYDTECH.HRMS.entites.Education;
import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.repository.EducationRepository;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EducationDTO saveOrUpdateEducation(String jwtToken, EducationDTO educationDTO) {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwtToken);
        Employee employee = employeeRepository.findByEmail(email);

        if (employee != null) {

            Education education = educationRepository.findByEmployee(employee);
            if (education == null) {

                education = modelMapper.map(educationDTO, Education.class);
                education.setEmployee(employee);
            } else {
               
                education.setDegree(educationDTO.getDegree());
                education.setInstitution(educationDTO.getInstitution());
                education.setUniversity(educationDTO.getUniversity());
                education.setStartYear(educationDTO.getStartYear());
                education.setEndYear(educationDTO.getEndYear());
            }
            education = educationRepository.save(education);
            return modelMapper.map(education, EducationDTO.class);
        }

        throw new RuntimeException("Employee not found with email: " + email);
    }

    @Override
    public boolean deleteEducation(String jwtToken) {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwtToken);
        Employee employee = employeeRepository.findByEmail(email);

        if (employee != null) {
            Education education = educationRepository.findByEmployee(employee);
            if (education != null) {
                educationRepository.delete(education);
                return true;
            }
        }

        return false;
    }

    @Override
    public EducationDTO fetchEducation(String jwtToken) {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwtToken);
        Employee employee = employeeRepository.findByEmail(email);

        if (employee != null) {
            Education education = educationRepository.findByEmployee(employee);
            if (education != null) {
                return modelMapper.map(education, EducationDTO.class);
            }
        }

        throw new RuntimeException("Education not found for the logged-in employee");
    }
}
