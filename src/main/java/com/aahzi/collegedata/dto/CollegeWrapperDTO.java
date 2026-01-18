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
public class CollegeWrapperDTO {
    @JsonProperty("college_details")
    private CollegeInfoDTO collegeDetails;

    @JsonProperty("course_details")
    private List<CourseInfoDTO> courseDetails;

    @JsonProperty("hostel_details")
    private List<HostelInfoDTO> hostelDetails;
}
