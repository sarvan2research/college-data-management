package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hostel_details")
public class HostelDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facility_type")
    private String facilityType;

    private String boys;
    private String girls;
    private String description;

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

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getBoys() {
        return boys;
    }

    public void setBoys(String boys) {
        this.boys = boys;
    }

    public String getGirls() {
        return girls;
    }

    public void setGirls(String girls) {
        this.girls = girls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollegeDetails getCollegeDetails() {
        return collegeDetails;
    }

    public void setCollegeDetails(CollegeDetails collegeDetails) {
        this.collegeDetails = collegeDetails;
    }
}
