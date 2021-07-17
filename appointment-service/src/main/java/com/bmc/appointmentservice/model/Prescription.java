package com.bmc.appointmentservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
public class Prescription {
    @Id
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
