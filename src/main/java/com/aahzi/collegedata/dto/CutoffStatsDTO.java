package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CutoffStatsDTO {
    @JsonProperty("min_mark")
    private Double minMark;
    @JsonProperty("max_mark")
    private Double maxMark;
    @JsonProperty("min_rank")
    private Integer minRank;
    @JsonProperty("max_rank")
    private Integer maxRank;
    @JsonProperty("total_admissions")
    private Integer totalAdmissions;

    public Double getMinMark() {
        return minMark;
    }

    public void setMinMark(Double minMark) {
        this.minMark = minMark;
    }

    public Double getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Double maxMark) {
        this.maxMark = maxMark;
    }

    public Integer getMinRank() {
        return minRank;
    }

    public void setMinRank(Integer minRank) {
        this.minRank = minRank;
    }

    public Integer getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(Integer maxRank) {
        this.maxRank = maxRank;
    }

    public Integer getTotalAdmissions() {
        return totalAdmissions;
    }

    public void setTotalAdmissions(Integer totalAdmissions) {
        this.totalAdmissions = totalAdmissions;
    }
}
