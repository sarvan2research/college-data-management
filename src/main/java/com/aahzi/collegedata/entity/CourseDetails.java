package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "course_details")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sl_no")
    private String slNo;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "approved_intake")
    private String approvedIntake;

    @Column(name = "year_of_starting")
    private String yearOfStarting;

    @Column(name = "nba_accredited")
    private String nbaAccredited;

    @Column(name = "accreditation_valid_upto")
    private String accreditationValidUpto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_details_id")
    private CollegeDetails collegeDetails;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CollegeDetails getCollegeDetails() {
        return collegeDetails;
    }

    public void setCollegeDetails(CollegeDetails collegeDetails) {
        this.collegeDetails = collegeDetails;
    }
}
