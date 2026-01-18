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
public class CollegeInfoDTO {
    @JsonProperty("college_code")
    private String collegeCode;
    @JsonProperty("college_name")
    private String collegeName;
    @JsonProperty("dean_principal")
    private String deanPrincipal;
    @JsonProperty("address")
    private String address;
    @JsonProperty("taluk")
    private String taluk;
    @JsonProperty("district")
    private String district;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("phone_fax")
    private String phoneFax;
    @JsonProperty("email_id")
    private String emailId;
    @JsonProperty("website")
    private String website;
    @JsonProperty("anti_ragging_phone")
    private String antiRaggingPhone;
    @JsonProperty("placement_percentage")
    private String placementPercentage;
    @JsonProperty("bank_account_no")
    private String bankAccountNo;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("distance_from_district_hq_km")
    private String distanceFromDistrictHqKm;
    @JsonProperty("nearest_railway_station")
    private String nearestRailwayStation;
    @JsonProperty("distance_from_railway_station_km")
    private String distanceFromRailwayStationKm;
    @JsonProperty("minority_status")
    private String minorityStatus;
    @JsonProperty("autonomous_status")
    private String autonomousStatus;
}
