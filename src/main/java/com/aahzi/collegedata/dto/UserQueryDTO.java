package com.aahzi.collegedata.dto;

import java.time.LocalDateTime;

public class UserQueryDTO {

    private Long id;
    private String name;
    private String mobileNumber;
    private String emailId;
    private String paymentMode;
    private String upiId;
    private String queryRequest;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private Integer version;

    public UserQueryDTO() {
    }

    public UserQueryDTO(Long id, String name, String mobileNumber, String emailId, String paymentMode, String upiId,
            String queryRequest, LocalDateTime timeCreated, LocalDateTime timeUpdated, Integer version) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.paymentMode = paymentMode;
        this.upiId = upiId;
        this.queryRequest = queryRequest;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(String queryRequest) {
        this.queryRequest = queryRequest;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
