package com.SPYDTECH.HRMS.controllers;



import com.SPYDTECH.HRMS.entites.Attendance;
import com.SPYDTECH.HRMS.entites.AttendanceReport;

import com.SPYDTECH.HRMS.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.saveAttendance(attendance));
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AttendanceReport>> getAllAttendancesRegardingEmpId(@RequestParam int month, @RequestParam int  year) {
        return new ResponseEntity<>(attendanceService.getAttendanceReportByEmpId(month,year), HttpStatus.OK);
    }

}
