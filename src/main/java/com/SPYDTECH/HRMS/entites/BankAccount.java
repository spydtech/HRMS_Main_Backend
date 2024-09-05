package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name ="bank_account")
public class BankAccount {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String accountHolderName;
        private String bankName;
        private String branchName;
        private String accountNumber;
        private String city;
        private String ifscCode;
        @OneToOne
        @JoinColumn(name = "employee_id", referencedColumnName = "id")
        private Employee employee;
}
