package com.bmc.appointmentservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Medicine {

    private String name;
    private String type;
    private String dosage;
    private String duration;
    private String frequency;
    private String remarks;
}
