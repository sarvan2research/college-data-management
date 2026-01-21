package com.aahzi.collegedata.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class SubjectInterest {

    private Double cse;
    private Double aids;
    private Double biomedicalEngineering;
    private Double chemicalEngineering;
    private Double civilEngineering;
    private Double ece;
    private Double eee;
    private Double it;
    private Double mechanicalEngineering;
    private Double mechatronicsEngineering;

    public SubjectInterest() {
    }

    public SubjectInterest(Double cse, Double aids, Double biomedicalEngineering,
            Double chemicalEngineering, Double civilEngineering, Double ece, Double eee, Double it,
            Double mechanicalEngineering, Double mechatronicsEngineering) {
        this.cse = cse;
        this.aids = aids;
        this.biomedicalEngineering = biomedicalEngineering;
        this.chemicalEngineering = chemicalEngineering;
        this.civilEngineering = civilEngineering;
        this.ece = ece;
        this.eee = eee;
        this.it = it;
        this.mechanicalEngineering = mechanicalEngineering;
        this.mechatronicsEngineering = mechatronicsEngineering;
    }

    public Double getCse() {
        return cse;
    }

    public void setCse(Double cse) {
        this.cse = cse;
    }

    public Double getAids() {
        return aids;
    }

    public void setAids(Double aids) {
        this.aids = aids;
    }

    public Double getBiomedicalEngineering() {
        return biomedicalEngineering;
    }

    public void setBiomedicalEngineering(Double biomedicalEngineering) {
        this.biomedicalEngineering = biomedicalEngineering;
    }

    public Double getChemicalEngineering() {
        return chemicalEngineering;
    }

    public void setChemicalEngineering(Double chemicalEngineering) {
        this.chemicalEngineering = chemicalEngineering;
    }

    public Double getCivilEngineering() {
        return civilEngineering;
    }

    public void setCivilEngineering(Double civilEngineering) {
        this.civilEngineering = civilEngineering;
    }

    public Double getEce() {
        return ece;
    }

    public void setEce(Double ece) {
        this.ece = ece;
    }

    public Double getEee() {
        return eee;
    }

    public void setEee(Double eee) {
        this.eee = eee;
    }

    public Double getIt() {
        return it;
    }

    public void setIt(Double it) {
        this.it = it;
    }

    public Double getMechanicalEngineering() {
        return mechanicalEngineering;
    }

    public void setMechanicalEngineering(Double mechanicalEngineering) {
        this.mechanicalEngineering = mechanicalEngineering;
    }

    public Double getMechatronicsEngineering() {
        return mechatronicsEngineering;
    }

    public void setMechatronicsEngineering(Double mechatronicsEngineering) {
        this.mechatronicsEngineering = mechatronicsEngineering;
    }

    @Override
    public String toString() {
        return "SubjectInterest{" +

                ", cse=" + cse +
                ", aids=" + aids +
                ", biomedicalEngineering=" + biomedicalEngineering +
                ", chemicalEngineering=" + chemicalEngineering +
                ", civilEngineering=" + civilEngineering +
                ", ece=" + ece +
                ", eee=" + eee +
                ", it=" + it +
                ", mechanicalEngineering=" + mechanicalEngineering +
                ", mechatronicsEngineering=" + mechatronicsEngineering +
                '}';
    }
}
