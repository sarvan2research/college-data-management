package com.aahzi.collegedata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    // Getters and Setters
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

    public String getDeanPrincipal() {
        return deanPrincipal;
    }

    public void setDeanPrincipal(String deanPrincipal) {
        this.deanPrincipal = deanPrincipal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhoneFax() {
        return phoneFax;
    }

    public void setPhoneFax(String phoneFax) {
        this.phoneFax = phoneFax;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAntiRaggingPhone() {
        return antiRaggingPhone;
    }

    public void setAntiRaggingPhone(String antiRaggingPhone) {
        this.antiRaggingPhone = antiRaggingPhone;
    }

    public String getPlacementPercentage() {
        return placementPercentage;
    }

    public void setPlacementPercentage(String placementPercentage) {
        this.placementPercentage = placementPercentage;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDistanceFromDistrictHqKm() {
        return distanceFromDistrictHqKm;
    }

    public void setDistanceFromDistrictHqKm(String distanceFromDistrictHqKm) {
        this.distanceFromDistrictHqKm = distanceFromDistrictHqKm;
    }

    public String getNearestRailwayStation() {
        return nearestRailwayStation;
    }

    public void setNearestRailwayStation(String nearestRailwayStation) {
        this.nearestRailwayStation = nearestRailwayStation;
    }

    public String getDistanceFromRailwayStationKm() {
        return distanceFromRailwayStationKm;
    }

    public void setDistanceFromRailwayStationKm(String distanceFromRailwayStationKm) {
        this.distanceFromRailwayStationKm = distanceFromRailwayStationKm;
    }

    public String getMinorityStatus() {
        return minorityStatus;
    }

    public void setMinorityStatus(String minorityStatus) {
        this.minorityStatus = minorityStatus;
    }

    public String getAutonomousStatus() {
        return autonomousStatus;
    }

    public void setAutonomousStatus(String autonomousStatus) {
        this.autonomousStatus = autonomousStatus;
    }
}
