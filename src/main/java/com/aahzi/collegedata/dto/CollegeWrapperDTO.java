package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollegeWrapperDTO {
    @JsonProperty("college_details")
    private CollegeInfoDTO collegeDetails;

    @JsonProperty("course_details")
    private List<CourseInfoDTO> courseDetails;

    @JsonProperty("hostel_details")
    private List<HostelInfoDTO> hostelDetails;

    public CollegeInfoDTO getCollegeDetails() {
        return collegeDetails;
    }

    public void setCollegeDetails(CollegeInfoDTO collegeDetails) {
        this.collegeDetails = collegeDetails;
    }

    public List<CourseInfoDTO> getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(List<CourseInfoDTO> courseDetails) {
        this.courseDetails = courseDetails;
    }

    public List<HostelInfoDTO> getHostelDetails() {
        return hostelDetails;
    }

    public void setHostelDetails(List<HostelInfoDTO> hostelDetails) {
        this.hostelDetails = hostelDetails;
    }
}
