package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollegeRootDTO {
    @JsonProperty("total_colleges")
    private Integer totalColleges;

    @JsonProperty("colleges")
    private List<CollegeWrapperDTO> colleges;

    public Integer getTotalColleges() {
        return totalColleges;
    }

    public void setTotalColleges(Integer totalColleges) {
        this.totalColleges = totalColleges;
    }

    public List<CollegeWrapperDTO> getColleges() {
        return colleges;
    }

    public void setColleges(List<CollegeWrapperDTO> colleges) {
        this.colleges = colleges;
    }
}
