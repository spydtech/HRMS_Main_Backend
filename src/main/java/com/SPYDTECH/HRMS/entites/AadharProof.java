package com.SPYDTECH.HRMS.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AadharProof {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Enumerated(EnumType.STRING)
    private IdType idType;

    private String idNumber;

    private String verified;

    private String submitted;

    @Lob
    private byte[] image;

    private String  employeeId;


}
