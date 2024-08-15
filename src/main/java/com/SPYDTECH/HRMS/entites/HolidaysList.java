package com.SPYDTECH.HRMS.entites;//package com.SPYDTECH.HRMS.entites;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Data
//@Entity
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Table(name ="holidays_list")
//public class HolidaysList {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private LocalDate day;
//    private String date;
//    private String holidayName;
//}
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "holidays_list")
public class HolidaysList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private LocalDate date;

    private String holidayName;
}

