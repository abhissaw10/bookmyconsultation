package com.bmc.ratingservice.event;

import lombok.Data;

@Data
public class RatingAddEvent {
    private String doctorId;
    public RatingAddEvent(String doctorId) {

        this.doctorId=doctorId;
    }
}
