package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.entites.LeaveModel;



public interface LeaveModelService {
    LeaveModel createLeave(LeaveModel leaveModel);
    public long getAllLeaveByMonth(String employeeId,int month,int year);

    public long getAllCasualLeaves(String employeeId);



}
