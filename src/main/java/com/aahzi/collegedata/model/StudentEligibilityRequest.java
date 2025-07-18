package com.aahzi.collegedata.model;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public class StudentEligibilityRequest {


    @NotNull
    private String name;

    @NotNull
    //@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number")
    private String mobileNumber;

    @NotNull
    private String community;

    @NotNull
    private String courseCode;

    private String district;

    @NotNull
    private BigDecimal mathsMarks;

    @NotNull
    private BigDecimal chemistryMarks;

    @NotNull
    private BigDecimal physicsMarks;

    @NotNull
    private BigDecimal cutoffMarks;

    public BigDecimal getCutoffMarks() {
        return cutoffMarks;
    }

    public void setCutoffMarks(BigDecimal cutoffMarks) {
        this.cutoffMarks = cutoffMarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public BigDecimal getMathsMarks() {
        return mathsMarks;
    }

    public void setMathsMarks(BigDecimal mathsMarks) {
        this.mathsMarks = mathsMarks;
    }

    public BigDecimal getChemistryMarks() {
        return chemistryMarks;
    }

    public void setChemistryMarks(BigDecimal chemistryMarks) {
        this.chemistryMarks = chemistryMarks;
    }

    public BigDecimal getPhysicsMarks() {
        return physicsMarks;
    }

    public void setPhysicsMarks(BigDecimal physicsMarks) {
        this.physicsMarks = physicsMarks;
    }

    @Override
    public String toString() {
        return "StudentEligibilityRequest{" +
                "name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", community='" + community + '\'' +
                ", course='" + courseCode + '\'' +
                ", district='" + district + '\'' +
                ", mathsMarks=" + mathsMarks +
                ", chemistryMarks=" + chemistryMarks +
                ", physicsMarks=" + physicsMarks +
                ", cutoffMarks=" + cutoffMarks +
                '}';
    }
}
