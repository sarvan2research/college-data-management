package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "college_course_data")
public class CollegeCourseData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // Constructors
    public CollegeCourseData() {}

    public CollegeCourseData(String collegeCode, String collegeName, String courseCode,
                             String courseName, Integer admissionYear, BigDecimal cutOffOC,
                             BigDecimal cutOffBC, BigDecimal cutOffBCM, BigDecimal cutOffMBC,
                             BigDecimal cutOffMBCDNC, BigDecimal cutOffMBCV, BigDecimal cutOffSC,
                             BigDecimal cutOffST, BigDecimal cutOffSCA) {
        this.collegeCode = collegeCode;
        this.collegeName = collegeName;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.admissionYear = admissionYear;
        this.cutOffOC = cutOffOC;
        this.cutOffBC = cutOffBC;
        this.cutOffBCM = cutOffBCM;
        this.cutOffMBC = cutOffMBC;
        this.cutOffMBCDNC = cutOffMBCDNC;
        this.cutOffMBCV = cutOffMBCV;
        this.cutOffSC = cutOffSC;
        this.cutOffST = cutOffST;
        this.cutOffSCA = cutOffSCA;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCollegeCode() { return collegeCode; }
    public void setCollegeCode(String collegeCode) { this.collegeCode = collegeCode; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public Integer getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(Integer admissionYear) { this.admissionYear = admissionYear; }

    public BigDecimal getCutOffOC() { return cutOffOC; }
    public void setCutOffOC(BigDecimal cutOffOC) { this.cutOffOC = cutOffOC; }

    public BigDecimal getCutOffBC() { return cutOffBC; }
    public void setCutOffBC(BigDecimal cutOffBC) { this.cutOffBC = cutOffBC; }

    public BigDecimal getCutOffBCM() { return cutOffBCM; }
    public void setCutOffBCM(BigDecimal cutOffBCM) { this.cutOffBCM = cutOffBCM; }

    public BigDecimal getCutOffMBC() { return cutOffMBC; }
    public void setCutOffMBC(BigDecimal cutOffMBC) { this.cutOffMBC = cutOffMBC; }

    public BigDecimal getCutOffMBCDNC() { return cutOffMBCDNC; }
    public void setCutOffMBCDNC(BigDecimal cutOffMBCDNC) { this.cutOffMBCDNC = cutOffMBCDNC; }

    public BigDecimal getCutOffMBCV() { return cutOffMBCV; }
    public void setCutOffMBCV(BigDecimal cutOffMBCV) { this.cutOffMBCV = cutOffMBCV; }

    public BigDecimal getCutOffSC() { return cutOffSC; }
    public void setCutOffSC(BigDecimal cutOffSC) { this.cutOffSC = cutOffSC; }

    public BigDecimal getCutOffST() { return cutOffST; }
    public void setCutOffST(BigDecimal cutOffST) { this.cutOffST = cutOffST; }

    public BigDecimal getCutOffSCA() { return cutOffSCA; }
    public void setCutOffSCA(BigDecimal cutOffSCA) { this.cutOffSCA = cutOffSCA; }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getDistrict() {
        return district;
    }

    @Override
    public String toString() {
        return "CollegeCourseData{" +
                "id=" + id +
                ", collegeCode='" + collegeCode + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", district='" + district + '\'' +
                ", admissionYear=" + admissionYear +
                ", cutOffOC=" + cutOffOC +
                ", cutOffBC=" + cutOffBC +
                ", cutOffBCM=" + cutOffBCM +
                ", cutOffMBC=" + cutOffMBC +
                ", cutOffMBCDNC=" + cutOffMBCDNC +
                ", cutOffMBCV=" + cutOffMBCV +
                ", cutOffSC=" + cutOffSC +
                ", cutOffST=" + cutOffST +
                ", cutOffSCA=" + cutOffSCA +
                '}';
    }



}