package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.entites.Employee;
import com.SPYDTECH.HRMS.entites.LeaveModel;
import com.SPYDTECH.HRMS.repository.EmployeeRepository;
import com.SPYDTECH.HRMS.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaveModelServiceImpl  implements  LeaveModelService{

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public LeaveModel createLeave(LeaveModel leaveModel) {

        Employee employee = employeeRepository.findByEmployeeId(leaveModel.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        LeaveModel leave = new LeaveModel();
        leave.setLeaveType(leaveModel.getLeaveType());
        leave.setEndDate(leaveModel.getEndDate());
        leave.setStartDate(leaveModel.getStartDate());
        leave.setReason(leaveModel.getReason());
        leave.setStartDateSelectHalf(leaveModel.getStartDateSelectHalf());
        leave.setEndDateSelectHalf(leaveModel.getEndDateSelectHalf());
        leave.setEmployeeId(leaveModel.getEmployeeId());
        return leaveRepository.save(leave);
    }

    public long getAllLeaveByMonth(String employeeId,int month,int year){
        long lossOfPay=0;
        List<LeaveModel> leaveModelList=leaveRepository.findByEmployeeId(employeeId);
        for(LeaveModel leaveModel:leaveModelList){
            if((leaveModel.getStartDate().getMonthValue()==month && leaveModel.getStartDate().getYear()==year )||(leaveModel.getEndDate().getMonthValue()==month && leaveModel.getEndDate().getYear()==year )){

                if(leaveModel.getLeaveType().name().equals("EARNEDLEAVE")){
                    if(leaveModel.getStartDate().equals(leaveModel.getEndDate())){
                        lossOfPay+=1;
                    }else{
                        Long days=ChronoUnit.DAYS.between(leaveModel.getStartDate(),leaveModel.getEndDate());
                        lossOfPay+=days;

                    }


                }
            }

        }
        return lossOfPay;

    }

    public long getAllCasualLeaves(String employeeId){
        List<LeaveModel> leaves=leaveRepository.findByEmployeeId(employeeId);
        long days=0;
        for(LeaveModel leaveModel:leaves){
            if(leaveModel.getLeaveType().name().equals("CASUALLEAVE")){
               //  days= ChronoUnit.DAYS.between(leaveModel.getStartDate(),leaveModel.getEndDate());
                days+=1;
            }
        }
       return days;

    }

}
