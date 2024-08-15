package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.entites.LeaveModel;
import com.SPYDTECH.HRMS.service.LeaveModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
public class LeaveModelController {

    @Autowired
    private LeaveModelService leaveModelService;

    @PostMapping("/create")
    public ResponseEntity<LeaveModel> createLeave(@RequestBody LeaveModel leaveModel){
        return new ResponseEntity<>(leaveModelService.createLeave(leaveModel), HttpStatus.CREATED);
    }
}
