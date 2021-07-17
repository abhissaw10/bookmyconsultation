package com.bmc.appointmentservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentDetails {
    private String id;
    private String appointmentId;
    private Date createdDate;
}
