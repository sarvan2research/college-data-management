package com.aahzi.collegedata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_query")
public class UserQuery extends BaseEntity {

    private String name;
    private String mobileNumber;
    private String emailId;
    private String paymentMode;
    private String upiId;
    private String queryRequest;

    public UserQuery() {
    }

    public UserQuery(String name, String mobileNumber, String emailId, String paymentMode, String upiId,
            String queryRequest) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.paymentMode = paymentMode;
        this.upiId = upiId;
        this.queryRequest = queryRequest;
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

    @Override
    public String toString() {
        return "UserQuery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", upiId='" + upiId + '\'' +
                ", queryRequest='" + queryRequest + '\'' +
                ", timeCreated=" + timeCreated +
                ", timeUpdated=" + timeUpdated +
                ", version=" + version +
                '}';
    }
}
