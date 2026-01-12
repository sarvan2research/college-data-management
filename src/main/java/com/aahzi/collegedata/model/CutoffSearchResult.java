package com.aahzi.collegedata.model;

import com.aahzi.collegedata.entity.CutoffStats;

public class CutoffSearchResult {
    private String collegeCode;
    private String collegeName;
    private String branchCode;
    private String branchName;
    private String community;
    private CutoffStats cutoffStats;

    public CutoffSearchResult() {
    }

    public CutoffSearchResult(String collegeCode, String collegeName, String branchCode, String branchName,
            String community, CutoffStats cutoffStats) {
        this.collegeCode = collegeCode;
        this.collegeName = collegeName;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.community = community;
        this.cutoffStats = cutoffStats;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public CutoffStats getCutoffStats() {
        return cutoffStats;
    }

    public void setCutoffStats(CutoffStats cutoffStats) {
        this.cutoffStats = cutoffStats;
    }

    @Override
    public String toString() {
        return "CutoffSearchResult{" +
                "collegeCode='" + collegeCode + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", community='" + community + '\'' +
                ", cutoffStats=" + cutoffStats +
                '}';
    }
}
