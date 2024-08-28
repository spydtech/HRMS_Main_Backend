package com.SPYDTECH.HRMS.service;



import com.SPYDTECH.HRMS.dto.DailyAttendanceDTO;
import com.SPYDTECH.HRMS.dto.EmployeeAttendanceDTO;
import com.SPYDTECH.HRMS.entites.Attendance;
import com.SPYDTECH.HRMS.entites.AttendanceReport;
import com.SPYDTECH.HRMS.entites.HolidaysList;
import com.SPYDTECH.HRMS.entites.LeaveModel;
import com.SPYDTECH.HRMS.repository.AttendanceReportRepository;
import com.SPYDTECH.HRMS.repository.AttendanceRepository;
import com.SPYDTECH.HRMS.repository.HolidayRepository;
import com.SPYDTECH.HRMS.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    HolidayService holidayService;

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveModelService leaveModelService;

    @Autowired
    AttendanceReportRepository attendanceReportRepository;

    public List<Attendance>  getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public Attendance saveAttendance(Attendance attendance) {
        Duration duration = Duration.between(attendance.getPunchIn(), attendance.getPunchOut());
        long hours = duration.toHours();
        long minutes=duration.toMinutes();
        long seconds= duration.toSeconds();
        attendance.setProductionHours(hours);
        attendance.setProductionMinutes(minutes);
        attendance.setProductionSeconds(seconds);
        if(hours>9){
            attendance.setOvertime(hours-9);
        }
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public List<AttendanceReport> getAttendanceReportByEmpId(int month, int year) {
        List<AttendanceReport> attendanceReportList = new ArrayList<>();

        List<Attendance> attendanceList = attendanceRepository.findAllByMonth(month, year);
        Set<String> empIds = attendanceList.stream().map(Attendance::getEmployeeId).collect(Collectors.toSet());
        List<HolidaysList> holidaysLists = holidayService.getListOfHolidaysByMonth(month, year);

        for (String employeeId : empIds) {
            List<Attendance> employeeAttendanceList = attendanceList.stream()
                    .filter(a -> a.getEmployeeId().equals(employeeId))
                    .collect(Collectors.toList());

            long lossOfPay = leaveModelService.getAllLeaveByMonth(employeeId, month, year);
            Long countOfCasual=leaveModelService.getAllCasualLeaves(employeeId);

            AttendanceReport attendanceReport = new AttendanceReport();
            long attendedDays = 0;
            long overTime = 0;
            String employeeName = "";
            long lateCount = 0;
            long earlyCount = 0;

            for (Attendance attendance : employeeAttendanceList) {
                employeeName = attendance.getEmployeeName();

                if (attendance.getProductionHours() >= 9) {
                    attendedDays += 1;

                }
                if(attendance.getProductionHours()==0){
                    lossOfPay+=1;
                }
                if (attendance.getOvertime() != 0) {
                    overTime += 1;
                }
                if (attendance.getPunchIn().toLocalTime().isAfter(LocalTime.of(9, 0, 0))) {
                    lateCount += 1;
                }
                if (attendance.getPunchIn().toLocalTime().isBefore(LocalTime.of(9, 0, 0))) {
                    earlyCount += 1;
                }
            }


            attendanceReport.setAttendedDays(attendedDays+countOfCasual);
            attendanceReport.setEmployeeName(employeeName);
            attendanceReport.setEmployeeId(employeeId);
            attendanceReport.setOverTime(overTime);
            attendanceReport.setLateCount(lateCount);
            attendanceReport.setEarlyCount(earlyCount);
            attendanceReport.setHolidays(holidaysLists.size());
            attendanceReport.setWeeklyOffs(8);
            attendanceReport.setLop(lossOfPay);
            attendanceReportRepository.save(attendanceReport);
            attendanceReportList.add(attendanceReport);
        }
        return attendanceReportList;
    }





}
