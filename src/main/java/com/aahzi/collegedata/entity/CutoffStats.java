package com.aahzi.collegedata.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class CutoffStats {

    private Double minMark;
    private Double maxMark;
    private Integer minRank;
    private Integer maxRank;
    private Integer totalAdmissions;

    public CutoffStats() {
    }

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

    @Override
    public String toString() {
        return "CutoffStats{" +
                "minMark=" + minMark +
                ", maxMark=" + maxMark +
                ", minRank=" + minRank +
                ", maxRank=" + maxRank +
                ", totalAdmissions=" + totalAdmissions +
                '}';
    }
}
