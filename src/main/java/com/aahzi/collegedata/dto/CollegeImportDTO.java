package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollegeImportDTO {
    @JsonProperty("college_code")
    private String collegeCode;
    @JsonProperty("college_name")
    private String collegeName;
    @JsonProperty("general_category_cutoff")
    private CutoffStatsDTO generalCategoryCutoff;
    @JsonProperty("course_wise_cutoff")
    private List<CourseImportDTO> courseWiseCutoff;

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

    public CutoffStatsDTO getGeneralCategoryCutoff() {
        return generalCategoryCutoff;
    }

    public void setGeneralCategoryCutoff(CutoffStatsDTO generalCategoryCutoff) {
        this.generalCategoryCutoff = generalCategoryCutoff;
    }

    public List<CourseImportDTO> getCourseWiseCutoff() {
        return courseWiseCutoff;
    }

    public void setCourseWiseCutoff(List<CourseImportDTO> courseWiseCutoff) {
        this.courseWiseCutoff = courseWiseCutoff;
    }
}
