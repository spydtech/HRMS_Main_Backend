package com.SPYDTECH.HRMS.controllers;

import com.SPYDTECH.HRMS.entites.AadharProof;
import com.SPYDTECH.HRMS.entites.IdType;
import com.SPYDTECH.HRMS.service.AadharProofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Create Aadhar Details
    @PostMapping("/save")
    public ResponseEntity<AadharProof> createAadharDetails(
            @RequestParam("idType") IdType idType,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("employeeId") String employeeId) throws IOException {
        AadharProof aadharProof = aadharProofService.createAadharDetails(idType, idNumber, employeeId);
        return ResponseEntity.ok(aadharProof);
    }

    // Update Aadhar Details
    @PutMapping("/update")
    public ResponseEntity<AadharProof> updateAadharDetails(
            @RequestParam("idType") IdType idType,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("verified") String verified,
            @RequestParam("submitted") String submitted,
            @RequestParam("employeeId") String employeeId,
            @RequestParam("file") MultipartFile file) throws IOException {
        AadharProof updatedAadharProof = aadharProofService.updateAadharDetails(idType, idNumber, verified, submitted, employeeId, file);
        return ResponseEntity.ok(updatedAadharProof);
    }


    @GetMapping("/get/{employeeId}")
    public ResponseEntity<List<AadharProof>> getAadharDetailsByEmployeeId(@PathVariable String employeeId) {
        List<AadharProof> aadharDetails = aadharProofService.getAadharDetailsByEmployeeId(employeeId);
        return ResponseEntity.ok(aadharDetails);
    }
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteByEmployeeId(@PathVariable String employeeId){
        return new ResponseEntity<>(aadharProofService.deleteProofs(employeeId), HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/{idType}")
    public ResponseEntity<String> deleteProofByEmployeeIdAndIdType(@PathVariable String employeeId, @PathVariable IdType idType){
        return new ResponseEntity<>(aadharProofService.deleteProofByEmployeeIdAndIdType(employeeId,idType),HttpStatus.OK); 
    }
}
