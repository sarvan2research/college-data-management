package com.aahzi.collegedata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hostel_details")
@Getter
@Setter
public class HostelDetails extends BaseEntity {

    @Column(name = "facility_type")
    private String facilityType;

    private String boys;
    private String girls;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_details_id")
    private CollegeDetails collegeDetails;
}
