package com.bmc.ratingservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Rating {

    @Id
    private String id;
    private String doctorId;
    private Integer rating;
}
