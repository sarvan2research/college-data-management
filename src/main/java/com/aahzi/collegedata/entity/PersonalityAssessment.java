package com.aahzi.collegedata.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class PersonalityAssessment {

    private Double logicalThinking;
    private Double communicationSkills;
    private Double analyticalSkills;
    private Double attitude;

    public PersonalityAssessment() {
    }

    public PersonalityAssessment(Double logicalThinking, Double communicationSkills, Double analyticalSkills,
            Double attitude) {
        this.logicalThinking = logicalThinking;
        this.communicationSkills = communicationSkills;
        this.analyticalSkills = analyticalSkills;
        this.attitude = attitude;
    }

    public Double getLogicalThinking() {
        return logicalThinking;
    }

    public void setLogicalThinking(Double logicalThinking) {
        this.logicalThinking = logicalThinking;
    }

    public Double getCommunicationSkills() {
        return communicationSkills;
    }

    public void setCommunicationSkills(Double communicationSkills) {
        this.communicationSkills = communicationSkills;
    }

    public Double getAnalyticalSkills() {
        return analyticalSkills;
    }

    public void setAnalyticalSkills(Double analyticalSkills) {
        this.analyticalSkills = analyticalSkills;
    }

    public Double getAttitude() {
        return attitude;
    }

    public void setAttitude(Double attitude) {
        this.attitude = attitude;
    }

    @Override
    public String toString() {
        return "PersonalityAssessment{" +
                "logicalThinking=" + logicalThinking +
                ", communicationSkills=" + communicationSkills +
                ", analyticalSkills=" + analyticalSkills +
                ", attitude=" + attitude +
                '}';
    }
}
