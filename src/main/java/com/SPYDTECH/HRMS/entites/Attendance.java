package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name ="attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String employeeId;
    private String employeeName;

    private LocalDateTime punchIn;

    private  LocalDateTime punchOut;

    private long productionHours;
    private long productionMinutes;
    private long productionSeconds;
    private long breakHours;
    private long breakMinutes;
    private long breakSeconds;
    private long overtime;

    private long workingHours;
    private long workingMinutes;
    private long workingSeconds;




}
