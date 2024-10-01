package com.SPYDTECH.HRMS.repository;

import com.SPYDTECH.HRMS.entites.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByDepartmentName(String departmentName);

    Optional<Department> findByDepartmentName(String department);

    void deleteByDepartmentName(String depName);

    @Query("SELECT d.departmentHead FROM Department d WHERE d.departmentName = ?1")
    Optional<String> findDepartmentHeadByName(String departmentName);





    @Query("SELECT d.departmentName FROM Department  d WHERE d.departmentHead = ?1")
    Optional <String>findDepartmentNameByHead(String departmentHead);


}
