package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseInfoDTO {
    @JsonProperty("sl_no")
    private String slNo;
    @JsonProperty("branch_code")
    private String branchCode;
    @JsonProperty("approved_intake")
    private String approvedIntake;
    @JsonProperty("year_of_starting")
    private String yearOfStarting;
    @JsonProperty("nba_accredited")
    private String nbaAccredited;
    @JsonProperty("accreditation_valid_upto")
    private String accreditationValidUpto;

    public String getSlNo() {
        return slNo;
    }

    public void setSlNo(String slNo) {
        this.slNo = slNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getApprovedIntake() {
        return approvedIntake;
    }

    public void setApprovedIntake(String approvedIntake) {
        this.approvedIntake = approvedIntake;
    }

    public String getYearOfStarting() {
        return yearOfStarting;
    }

    public void setYearOfStarting(String yearOfStarting) {
        this.yearOfStarting = yearOfStarting;
    }

    public String getNbaAccredited() {
        return nbaAccredited;
    }

    public void setNbaAccredited(String nbaAccredited) {
        this.nbaAccredited = nbaAccredited;
    }

    public String getAccreditationValidUpto() {
        return accreditationValidUpto;
    }

    public void setAccreditationValidUpto(String accreditationValidUpto) {
        this.accreditationValidUpto = accreditationValidUpto;
    }
}
