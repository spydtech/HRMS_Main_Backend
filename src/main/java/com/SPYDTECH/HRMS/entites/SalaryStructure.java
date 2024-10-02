package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name ="salary_structure")
public class SalaryStructure {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "effective_date", nullable = false)
        private LocalDate effectiveDate;
        @ManyToOne
        @JoinColumn(name = "employee")
        private Employee employee;
        @ElementCollection
        @CollectionTable(name = "salary_components_yearly", joinColumns = @JoinColumn(name = "salary_structure_id"))
        @MapKeyColumn(name = "component_name")
        @Column(name = "yearly_amount")
        private Map<String, BigDecimal> salaryComponentsYearly;
        @ElementCollection
        @CollectionTable(name = "salary_components_monthly", joinColumns = @JoinColumn(name = "salary_structure_id"))
        @MapKeyColumn(name = "component_name")
        @Column(name = "monthly_amount")
        private Map<String, BigDecimal> salaryComponentsMonthly;
}
