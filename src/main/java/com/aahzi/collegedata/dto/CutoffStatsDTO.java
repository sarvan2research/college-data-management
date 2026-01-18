package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
