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
public class CourseInfoDTO {
    @JsonProperty("sl_no")
    private String slNo;
    @JsonProperty("branch_code")
    private String branchCode;
    @JsonProperty("approved_intake")
    private String approvedIntake;
    @JsonProperty("year_of_starting")
    private String yearOfStarting;
    @JsonProperty("nba_accredited")
    private String nbaAccredited;
    @JsonProperty("accreditation_valid_upto")
    private String accreditationValidUpto;
}
