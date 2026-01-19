package com.aahzi.collegedata.model;

import java.util.List;

public class HistoricalCutoffDTO {
    private String collegeCode;
    private String branchCode;
    private String community;
    private List<CutoffYearlyData> yearlyData;

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public List<CutoffYearlyData> getYearlyData() {
        return yearlyData;
    }

    public void setYearlyData(List<CutoffYearlyData> yearlyData) {
        this.yearlyData = yearlyData;
    }
}
