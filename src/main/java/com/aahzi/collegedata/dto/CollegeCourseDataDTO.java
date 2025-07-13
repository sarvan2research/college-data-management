package com.aahzi.collegedata.dto;

import java.math.BigDecimal;

public class CollegeCourseDataDTO {
    private Long id;
    private String collegeCode;
    private String collegeName;
    private String courseCode;
    private String courseName;
    private Integer admissionYear;
    private BigDecimal cutOffOC;
    private BigDecimal cutOffBC;
    private BigDecimal cutOffBCM;
    private BigDecimal cutOffMBC;
    private BigDecimal cutOffMBCDNC;
    private BigDecimal cutOffMBCV;
    private BigDecimal cutOffSC;
    private BigDecimal cutOffST;
    private BigDecimal cutOffSCA;

    // Constructors
    public CollegeCourseDataDTO() {}

    // Getters and Setters (same as entity)
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
}

