package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HostelInfoDTO {
    @JsonProperty("facility_type")
    private String facilityType;
    @JsonProperty("boys")
    private String boys;
    @JsonProperty("girls")
    private String girls;
    @JsonProperty("description")
    private String description;

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
}
