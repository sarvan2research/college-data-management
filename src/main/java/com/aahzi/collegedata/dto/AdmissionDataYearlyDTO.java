package com.aahzi.collegedata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionDataYearlyDTO {
    private Long id;
    private String collegeCode;
    private String collegeName;
    private String courseCode;
    private String courseName;
    private Integer admissionYear;

    // Cutoff fields
    private BigDecimal cutOffOC;
    private BigDecimal cutOffBC;
    private BigDecimal cutOffBCM;
    private BigDecimal cutOffMBC;
    private BigDecimal cutOffMBCDNC;
    private BigDecimal cutOffMBCV;
    private BigDecimal cutOffSC;
    private BigDecimal cutOffST;
    private BigDecimal cutOffSCA;

    // Rank fields
    private Integer rankOC;
    private Integer rankBC;
    private Integer rankBCM;
    private Integer rankMBC;
    private Integer rankMBCDNC;
    private Integer rankMBCV;
    private Integer rankSC;
    private Integer rankST;
    private Integer rankSCA;
}
