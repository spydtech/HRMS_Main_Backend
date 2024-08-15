package com.SPYDTECH.HRMS.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HolidayRequest {
    private Long id;
    private String day;
    private LocalDate date;
    private String holidayName;
}
