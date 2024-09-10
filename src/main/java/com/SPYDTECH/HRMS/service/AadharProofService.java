package com.SPYDTECH.HRMS.service;

import com.SPYDTECH.HRMS.entites.AadharProof;
import com.SPYDTECH.HRMS.entites.IdType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AadharProofService {
    AadharProof createAadharDetails(IdType idType, String idNumber
            , String employeeId/**, MultipartFile file**/) throws IOException;

    AadharProof updateAadharDetails(IdType idType, String idNumber, String verified, String submitted
            , String employeeId, MultipartFile file) throws IOException;

    List<AadharProof> getAadharDetailsByEmployeeId(String employeeId);

    String deleteProofs(String employeeId);

    String deleteProofByEmployeeIdAndIdType(String employeeId,IdType idType);
}
