package com.bmc.appointmentservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimeSlots {
    private String availableDate;
    private List<String> timeSlots;

}
