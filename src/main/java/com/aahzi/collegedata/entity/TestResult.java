package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;
    private String name;
    private String email;
    private String timestamp;
    private String timeUsed;

    @Embedded
    private PersonalityAssessment personalityAssessment;

    @Embedded
    private SubjectInterest subjectInterest;

    private Double personalityTotal;
    private Double subjectTotal;

    public TestResult() {
    }

    public TestResult(String studentId, String name, String email, String timestamp, String timeUsed,
            PersonalityAssessment personalityAssessment, SubjectInterest subjectInterest, Double personalityTotal,
            Double subjectTotal) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.timestamp = timestamp;
        this.timeUsed = timeUsed;
        this.personalityAssessment = personalityAssessment;
        this.subjectInterest = subjectInterest;
        this.personalityTotal = personalityTotal;
        this.subjectTotal = subjectTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public PersonalityAssessment getPersonalityAssessment() {
        return personalityAssessment;
    }

    public void setPersonalityAssessment(PersonalityAssessment personalityAssessment) {
        this.personalityAssessment = personalityAssessment;
    }

    public SubjectInterest getSubjectInterest() {
        return subjectInterest;
    }

    public void setSubjectInterest(SubjectInterest subjectInterest) {
        this.subjectInterest = subjectInterest;
    }

    public Double getPersonalityTotal() {
        return personalityTotal;
    }

    public void setPersonalityTotal(Double personalityTotal) {
        this.personalityTotal = personalityTotal;
    }

    public Double getSubjectTotal() {
        return subjectTotal;
    }

    public void setSubjectTotal(Double subjectTotal) {
        this.subjectTotal = subjectTotal;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", timeUsed='" + timeUsed + '\'' +
                ", personalityTotal=" + personalityTotal +
                ", subjectTotal=" + subjectTotal +
                '}';
    }
}
