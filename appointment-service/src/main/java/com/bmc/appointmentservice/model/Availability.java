package com.bmc.appointmentservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Availability {

    private String doctorId;
    private Map<String,List<String>> availabilityMap;
}
