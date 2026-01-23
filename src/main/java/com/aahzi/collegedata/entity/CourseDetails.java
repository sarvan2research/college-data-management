package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_details")
@Getter
@Setter
public class CourseDetails extends BaseEntity {

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
}
