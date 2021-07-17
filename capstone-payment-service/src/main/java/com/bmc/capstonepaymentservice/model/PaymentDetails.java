package com.bmc.capstonepaymentservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class PaymentDetails {
    @Id
    private String id;
    private String appointmentId;
    private Date createDate;

}
