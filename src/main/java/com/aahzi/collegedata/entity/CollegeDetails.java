package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "college_details")
@Getter
@Setter
public class CollegeDetails extends BaseEntity {

    @Column(unique = true, name = "college_code")
    private String collegeCode;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "dean_principal")
    private String deanPrincipal;

    private String address;
    private String taluk;
    private String district;
    private String pincode;

    @Column(name = "phone_fax")
    private String phoneFax;

    @Column(name = "email_id")
    private String emailId;

    private String website;

    @Column(name = "anti_ragging_phone")
    private String antiRaggingPhone;

    @Column(name = "placement_percentage")
    private String placementPercentage;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "distance_from_district_hq_km")
    private String distanceFromDistrictHqKm;

    @Column(name = "nearest_railway_station")
    private String nearestRailwayStation;

    @Column(name = "distance_from_railway_station_km")
    private String distanceFromRailwayStationKm;

    @Column(name = "minority_status")
    private String minorityStatus;

    @Column(name = "autonomous_status")
    private String autonomousStatus;

    @OneToMany(mappedBy = "collegeDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseDetails> courseDetails;

    @OneToMany(mappedBy = "collegeDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HostelDetails> hostelDetails;
}
