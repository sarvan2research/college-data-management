package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "AdmissionDataYearly", uniqueConstraints = @UniqueConstraint(columnNames = { "admission_year",
        "college_code", "course_code" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionDataYearly extends BaseEntity {

    @Column(name = "college_code")
    private String collegeCode;

    @Column(name = "college_name", length = 500)
    private String collegeName;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name", length = 200)
    private String courseName;

    @Column(name = "district", length = 200)
    private String district;

    @Column(name = "admission_year")
    private Integer admissionYear;

    // Cutoff fields
    @Column(name = "cut_off_oc", precision = 10, scale = 3)
    private BigDecimal cutOffOC;

    @Column(name = "cut_off_bc", precision = 10, scale = 3)
    private BigDecimal cutOffBC;

    @Column(name = "cut_off_bcm", precision = 10, scale = 3)
    private BigDecimal cutOffBCM;

    @Column(name = "cut_off_mbc", precision = 10, scale = 3)
    private BigDecimal cutOffMBC;

    @Column(name = "cut_off_mbc_dnc", precision = 10, scale = 3)
    private BigDecimal cutOffMBCDNC;

    @Column(name = "cut_off_mbc_v", precision = 10, scale = 3)
    private BigDecimal cutOffMBCV;

    @Column(name = "cut_off_sc", precision = 10, scale = 3)
    private BigDecimal cutOffSC;

    @Column(name = "cut_off_st", precision = 10, scale = 3)
    private BigDecimal cutOffST;

    @Column(name = "cut_off_sca", precision = 10, scale = 3)
    private BigDecimal cutOffSCA;

    // Rank fields
    @Column(name = "rank_oc")
    private Integer rankOC;

    @Column(name = "rank_bc")
    private Integer rankBC;

    @Column(name = "rank_bcm")
    private Integer rankBCM;

    @Column(name = "rank_mbc")
    private Integer rankMBC;

    @Column(name = "rank_mbc_dnc")
    private Integer rankMBCDNC;

    @Column(name = "rank_mbc_v")
    private Integer rankMBCV;

    @Column(name = "rank_sc")
    private Integer rankSC;

    @Column(name = "rank_st")
    private Integer rankST;

    @Column(name = "rank_sca")
    private Integer rankSCA;

    @Override
    public String toString() {
        return "AdmissionDataYearly{" +
                "id=" + id +
                ", collegeCode='" + collegeCode + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", district='" + district + '\'' +
                ", admissionYear=" + admissionYear +
                ", cutOffOC=" + cutOffOC +
                ", rankOC=" + rankOC +
                '}';
    }
}