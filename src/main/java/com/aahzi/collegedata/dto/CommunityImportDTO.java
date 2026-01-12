package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityImportDTO {
    @JsonProperty("community")
    private String community;
    @JsonProperty("cutoff")
    private CutoffStatsDTO cutoff;

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public CutoffStatsDTO getCutoff() {
        return cutoff;
    }

    public void setCutoff(CutoffStatsDTO cutoff) {
        this.cutoff = cutoff;
    }
}
