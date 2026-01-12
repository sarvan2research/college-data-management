package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "student_data")
public class StudentData extends BaseEntity {

    @Column(name = "student_name")
    private String name;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "community")
    private String community;
    @Column(name = "course_name")
    private String course;
    @Column(name = "district")
    private String district;
    @Column(name = "maths")
    private BigDecimal maths;
    @Column(name = "chemistry")
    private BigDecimal chemistry;
    @Column(name = "physics")
    private BigDecimal physics;

    public StudentData() {
    }

    public StudentData(Long id, String name, String mobileNumber, String community, String course, String district,
            BigDecimal maths, BigDecimal chemistry, BigDecimal physics) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.community = community;
        this.course = course;
        this.district = district;
        this.maths = maths;
        this.chemistry = chemistry;
        this.physics = physics;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public BigDecimal getMaths() {
        return maths;
    }

    public void setMaths(BigDecimal maths) {
        this.maths = maths;
    }

    public BigDecimal getChemistry() {
        return chemistry;
    }

    public void setChemistry(BigDecimal chemistry) {
        this.chemistry = chemistry;
    }

    public BigDecimal getPhysics() {
        return physics;
    }

    public void setPhysics(BigDecimal physics) {
        this.physics = physics;
    }

    @Override
    public String toString() {
        return "StudentData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", community='" + community + '\'' +
                ", course='" + course + '\'' +
                ", district='" + district + '\'' +
                ", maths=" + maths +
                ", chemistry=" + chemistry +
                ", physics=" + physics +
                '}';
    }
}