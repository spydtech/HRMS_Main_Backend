package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private SelectHalf startDateSelectHalf;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private SelectHalf endDateSelectHalf;

    private String reason;

    private String employeeId;












}
