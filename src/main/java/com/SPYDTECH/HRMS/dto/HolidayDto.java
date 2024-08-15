package com.SPYDTECH.HRMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDto {
    private Long id;
    private String day;
    private String date;
    private String holidayName;
}
