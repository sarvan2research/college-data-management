package com.aahzi.collegedata.model;

import java.math.BigDecimal;

public class CutoffYearlyData {
    private Integer year;
    private BigDecimal cutoffMark;
    private Integer closingRank;
    private boolean available;
    private BigDecimal minMark;
    private BigDecimal maxMark;
    private Integer minRank;
    private Integer maxRank;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getCutoffMark() {
        return cutoffMark;
    }

    public void setCutoffMark(BigDecimal cutoffMark) {
        this.cutoffMark = cutoffMark;
    }

    public Integer getClosingRank() {
        return closingRank;
    }

    public void setClosingRank(Integer closingRank) {
        this.closingRank = closingRank;
    }

    public BigDecimal getMinMark() {
        return minMark;
    }

    public void setMinMark(BigDecimal minMark) {
        this.minMark = minMark;
    }

    public BigDecimal getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(BigDecimal maxMark) {
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
