package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private Date startDate;

    @Enumerated(EnumType.STRING)
    private SelectHalf selectHalf;

    private Date endDate;

    private int noOfDays;

    private String reason;

    private String employeeId;

}
