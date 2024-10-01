package com.SPYDTECH.HRMS.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DepartmentRequest {
    private Integer id;
    private String departmentName;
    private String departmentHead;
    private Long totalEmployee;
}
