package com.aahzi.collegedata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_query")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery extends BaseEntity {

    private String name;
    private String mobileNumber;
    private String emailId;
    private String paymentMode;
    private String upiId;
    private String queryRequest;

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
