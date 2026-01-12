package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseImportDTO {
    @JsonProperty("branch_code")
    private String branchCode;
    @JsonProperty("branch_name")
    private String branchName;
    @JsonProperty("overall_cutoff")
    private CutoffStatsDTO overallCutoff;
    @JsonProperty("community_wise_cutoff")
    private List<CommunityImportDTO> communityWiseCutoff;

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

    public CutoffStatsDTO getOverallCutoff() {
        return overallCutoff;
    }

    public void setOverallCutoff(CutoffStatsDTO overallCutoff) {
        this.overallCutoff = overallCutoff;
    }

    public List<CommunityImportDTO> getCommunityWiseCutoff() {
        return communityWiseCutoff;
    }

    public void setCommunityWiseCutoff(List<CommunityImportDTO> communityWiseCutoff) {
        this.communityWiseCutoff = communityWiseCutoff;
    }
}
