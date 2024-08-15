package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long id;
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
