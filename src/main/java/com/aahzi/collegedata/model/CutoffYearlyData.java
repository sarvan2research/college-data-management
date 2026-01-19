package com.aahzi.collegedata.model;

import java.math.BigDecimal;

public class CutoffYearlyData {
    private Integer year;
    private BigDecimal cutoffMark;
    private Integer closingRank;
    private boolean available;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
