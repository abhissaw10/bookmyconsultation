package com.bmc.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

    private String name;
    private String type;
    private String dosage;
    private String duration;
    private String frequency;
    private String remarks;
}
