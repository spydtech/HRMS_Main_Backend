package com.SPYDTECH.HRMS.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceReport {
    private String employeeName;
    private String employeeId;
    private long attendedDays;
    private long weeklyOffs;
    private long holidays;
    private long timeOff;
    private long overTime;
    private long lop;
    private long lateCount;
    private long earlyCount;

}
