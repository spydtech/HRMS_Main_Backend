package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.entites.AadharProof;
import com.SPYDTECH.HRMS.entites.IdType;
import com.SPYDTECH.HRMS.service.AadharProofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aadhar")
public class AadharProofController {

    @Autowired
    private AadharProofService aadharProofService;

    @PostMapping("/save")
    public ResponseEntity<AadharProof> createAadharDetails(
            @RequestParam("idType") String idTypeStr,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("employeeId") String employeeId) throws IOException {

        IdType idType;
        try {
            idType = IdType.valueOf(idTypeStr.toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle invalid enum value
        }

        AadharProof aadharProof = aadharProofService.createAadharDetails(idType, idNumber, employeeId);
        return ResponseEntity.ok(aadharProof);
    }

    @PutMapping("/update")
    public ResponseEntity<AadharProof> updateAadharDetails(
            @RequestParam("idType") String idTypeStr,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("verified") String verified,
            @RequestParam("submitted") String submitted,
            @RequestParam("employeeId") String employeeId,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        IdType idType;
        try {
            idType = IdType.valueOf(idTypeStr.toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle invalid enum value
        }

        AadharProof updatedAadharProof = aadharProofService.updateAadharDetails(idType, idNumber, verified, submitted, employeeId, file);
        return ResponseEntity.ok(updatedAadharProof);
    }

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<List<AadharProof>> getAadharDetailsByEmployeeId(@PathVariable String employeeId) {
        List<AadharProof> aadharDetails = aadharProofService.getAadharDetailsByEmployeeId(employeeId);
        return ResponseEntity.ok(aadharDetails);
    }
}
