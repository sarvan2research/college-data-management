package com.aahzi.collegedata.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_results")
public class TestResult extends BaseEntity {

    private String studentId;
    private String name;
    private String email;
    private String timestamp;
    private String timeUsed;
    private String mobileNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "computerScience", column = @Column(name = "personality_computer_science")),
            @AttributeOverride(name = "maths", column = @Column(name = "personality_maths")),
            @AttributeOverride(name = "logicalThinking", column = @Column(name = "personality_logical_thinking")),
            @AttributeOverride(name = "communicationSkills", column = @Column(name = "personality_communication_skills")),
            @AttributeOverride(name = "analyticalSkills", column = @Column(name = "personality_analytical_skills")),
            @AttributeOverride(name = "attitude", column = @Column(name = "personality_attitude"))
    })
    private PersonalityAssessment personalityAssessment;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cse", column = @Column(name = "subject_cse")),
            @AttributeOverride(name = "aids", column = @Column(name = "subject_aids")),
            @AttributeOverride(name = "biomedicalEngineering", column = @Column(name = "subject_biomedical_engineering")),
            @AttributeOverride(name = "chemicalEngineering", column = @Column(name = "subject_chemical_engineering")),
            @AttributeOverride(name = "civilEngineering", column = @Column(name = "subject_civil_engineering")),
            @AttributeOverride(name = "ece", column = @Column(name = "subject_ece")),
            @AttributeOverride(name = "eee", column = @Column(name = "subject_eee")),
            @AttributeOverride(name = "it", column = @Column(name = "subject_it")),
            @AttributeOverride(name = "mechanicalEngineering", column = @Column(name = "subject_mechanical_engineering")),
            @AttributeOverride(name = "mechatronicsEngineering", column = @Column(name = "subject_mechatronics_engineering"))
    })
    private SubjectInterest subjectInterest;

    private Double personalityTotal;
    private Double subjectTotal;

    public TestResult() {
    }

    public TestResult(String studentId, String name, String email, String mobileNumber, String timestamp,
            String timeUsed,
            PersonalityAssessment personalityAssessment, SubjectInterest subjectInterest, Double personalityTotal,
            Double subjectTotal) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.timestamp = timestamp;
        this.timeUsed = timeUsed;
        this.personalityAssessment = personalityAssessment;
        this.subjectInterest = subjectInterest;
        this.personalityTotal = personalityTotal;
        this.subjectTotal = subjectTotal;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
                ", mobileNumber='" + mobileNumber + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", timeUsed='" + timeUsed + '\'' +
                ", personalityTotal=" + personalityTotal +
                ", subjectTotal=" + subjectTotal +
                '}';
    }
}
