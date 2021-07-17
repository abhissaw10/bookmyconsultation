package com.bmc.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

    private String id;
    private String userId;
    private String userEmailId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private String appointmentId;
    private String diagnosis;
    private List<Medicine> medicineList;
    private String comments;
    private String createdDate;
}
