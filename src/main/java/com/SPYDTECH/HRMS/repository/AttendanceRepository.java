package com.SPYDTECH.HRMS.repository;



import com.SPYDTECH.HRMS.entites.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {





    @Query("SELECT a FROM Attendance a WHERE FUNCTION('MONTH', a.punchIn) = :month AND FUNCTION('YEAR', a.punchIn) = :year")
    List<Attendance> findAllByMonth(@Param("month") int month, @Param("year") int year);

}
