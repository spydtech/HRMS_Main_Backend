package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.HolidayDto;
import com.SPYDTECH.HRMS.request.HolidayRequest;
import com.SPYDTECH.HRMS.entites.HolidaysList;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {


    public List<HolidaysList> getListOfHolidaysByMonth(int month, int year);
    boolean deleteHoliday(String name);
    public List<HolidaysList> getAllHolidays();
    HolidaysList save(HolidaysList holidaysList);
    HolidaysList updateHoliday(String name, HolidaysList holidaysListDetails);
}
