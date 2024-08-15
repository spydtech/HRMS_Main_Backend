package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.dto.HolidayDto;
import com.SPYDTECH.HRMS.request.HolidayRequest;
import com.SPYDTECH.HRMS.entites.HolidaysList;
import com.SPYDTECH.HRMS.exceptions.ErrorResponse;
import com.SPYDTECH.HRMS.repository.HolidayRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    HolidayRepository holidayRepository;
    public List<HolidaysList> getListOfHolidaysByMonth(int month,int year){
        List<HolidaysList>holidaysLists= holidayRepository.findAll();
        List<HolidaysList> result=new ArrayList<>();
        for(HolidaysList list:holidaysLists){
            int monthOfHoliday=list.getDate().getMonthValue();
            int yearOfHoliday=list.getDate().getYear();
            if(monthOfHoliday==month && yearOfHoliday==year){
                result.add(list);
            }


        }
        return result;
    }


    public boolean deleteHoliday(String holidayName) {
        Optional<HolidaysList> holidaysListOptional = holidayRepository.findByHolidayName(holidayName);

        if (!holidaysListOptional.isPresent()) {
            return false;
        }

        holidayRepository.delete(holidaysListOptional.get());
        return true;
    }

    public List<HolidaysList> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public HolidaysList save(HolidaysList holiday) {
        return holidayRepository.save(holiday);
    }
    public HolidaysList updateHoliday(String holidayName, HolidaysList holidaysListDetails) {
        Optional<HolidaysList> HolidaysListOptional = holidayRepository.findByHolidayName(holidayName);

        if (!HolidaysListOptional.isPresent()) {
            return null;
        }

        HolidaysList holidaysList = HolidaysListOptional.get();
        holidaysList.setDate(holidaysListDetails.getDate());
        holidaysList.setDay(holidaysListDetails.getDay());
        holidaysList.setHolidayName(holidaysListDetails.getHolidayName());

        return holidayRepository.save(holidaysList);
    }
}
