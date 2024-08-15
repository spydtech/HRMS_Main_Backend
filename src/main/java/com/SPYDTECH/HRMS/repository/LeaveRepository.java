package com.SPYDTECH.HRMS.repository;


import com.SPYDTECH.HRMS.entites.LeaveModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveModel,Long> {


    List<LeaveModel> findByEmployeeId(String employeeId);
}
