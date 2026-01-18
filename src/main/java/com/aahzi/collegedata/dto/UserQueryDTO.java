package com.aahzi.collegedata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
