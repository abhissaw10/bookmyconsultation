package com.bmc.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    private String appointmentId;
    private String doctorId;
    private String doctorName;
    private String userId;
    private String userName;
    private String userEmailId;
    private String timeSlot;
    private String status;
    private LocalDate appointmentDate;
    private String symptoms;
    private String priorMedicalHistory;

}
