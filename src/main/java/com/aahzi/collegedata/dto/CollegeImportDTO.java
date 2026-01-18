package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeImportDTO {
    @JsonProperty("college_code")
    private String collegeCode;
    @JsonProperty("college_name")
    private String collegeName;
    @JsonProperty("general_category_cutoff")
    private CutoffStatsDTO generalCategoryCutoff;
    @JsonProperty("course_wise_cutoff")
    private List<CourseImportDTO> courseWiseCutoff;
}
