package com.SPYDTECH.HRMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class EducationDTO {

    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String degree;
    @NotNull
    @NotEmpty
    @NotBlank
    private String institution;
    @NotNull
    @NotEmpty
    @NotBlank
    private String university;
    @NotNull
    @NotEmpty
    @NotBlank
    private int startYear;
    @NotEmpty
    @NotBlank
    private int endYear;

}

