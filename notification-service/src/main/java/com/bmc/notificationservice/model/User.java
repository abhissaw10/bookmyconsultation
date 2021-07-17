package com.bmc.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String dob;
    private String mobile;
    private String emailId;
    private String createdDate;
}
