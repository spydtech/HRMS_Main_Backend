package com.SPYDTECH.HRMS.entites;

//import com.SPYDTECH.HRMS.service.LocalDateAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name ="leave_request")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String employeeId;

    private String leaveType;

    // @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fromDate;


    private LocalDate endDate;

    private String reason;

    //private String rejectReason;

//    @Enumerated(EnumType.STRING)
//    private LeaveStatus leaveStatus = LeaveStatus.PENDING;
//
//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime requestCreationDate;
//
//    @Column(columnDefinition = "TIMESTAMP")
//    private LocalDateTime acceptOrRejectDate;
//
//
//
//    @PrePersist
//    protected void onCreate() {
//        this.requestCreationDate = LocalDateTime.now();
//    }
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;




}
