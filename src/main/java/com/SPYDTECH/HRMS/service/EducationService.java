package com.SPYDTECH.HRMS.service;


import com.SPYDTECH.HRMS.dto.EducationDTO;


public interface EducationService {


    EducationDTO saveOrUpdateEducation(String jwtToken, EducationDTO educationDTO);

    boolean deleteEducation(String jwtToken);

    EducationDTO fetchEducation(String jwtToken);
}


