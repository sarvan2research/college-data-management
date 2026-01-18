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
public class HostelInfoDTO {
    @JsonProperty("facility_type")
    private String facilityType;
    @JsonProperty("boys")
    private String boys;
    @JsonProperty("girls")
    private String girls;
    @JsonProperty("description")
    private String description;
}
