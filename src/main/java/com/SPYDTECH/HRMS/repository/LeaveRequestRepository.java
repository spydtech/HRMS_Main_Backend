package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.LeaveRequest;
import com.SPYDTECH.HRMS.entites.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {


    List<LeaveRequest> findAllByLeaveStatus(LeaveStatus leaveStatus);

    List<LeaveRequest> findByEmployeeId(String employeeId);

    LeaveRequest findByEmployeeIdAndFromDate(String employeeId, LocalDate fromDate);
}
