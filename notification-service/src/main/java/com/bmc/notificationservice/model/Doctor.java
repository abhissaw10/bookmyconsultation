package com.bmc.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String speciality;
    //TODO update the data type of dob
    private String dob;
    private String status;
    private String approvedBy;
    private String approverComments;
    private String registrationDate;
    private String verificationDate;

}
