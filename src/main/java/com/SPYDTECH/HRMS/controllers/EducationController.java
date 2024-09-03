package com.SPYDTECH.HRMS.controllers;


import com.SPYDTECH.HRMS.dto.EducationDTO;
import com.SPYDTECH.HRMS.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<EducationDTO> saveOrUpdateEducation(@Valid  @RequestHeader("Authorization") String jwt,
                                                              @RequestBody EducationDTO educationDTO) {
        EducationDTO savedEducation = educationService.saveOrUpdateEducation(jwt, educationDTO);
        return new ResponseEntity<>(savedEducation, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEducation(@RequestHeader("Authorization") String jwt) {
        boolean isDeleted = educationService.deleteEducation(jwt);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fetchEducation")
    public ResponseEntity<EducationDTO> fetchEducation(@RequestHeader("Authorization") String jwt) {
        EducationDTO educationDTO = educationService.fetchEducation(jwt);
        return new ResponseEntity<>(educationDTO, HttpStatus.OK);
    }
}
