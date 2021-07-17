package com.bmc.ratingservice.event;

import com.bmc.ratingservice.model.AverageRating;
import com.bmc.ratingservice.model.Rating;
import com.bmc.ratingservice.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RatingAddListener {

    private final RatingsRepository ratingsRepository;
    private final KafkaTemplate<String, AverageRating> kafkaTemplate;

    @Value("${doctor.rating}")
    private String doctorRatingTopic;

    @EventListener
    public void handleAddRatingEvent(RatingAddEvent event){
        Double avgRating = ratingsRepository
            .findByDoctorId(event.getDoctorId())
            .stream()
            .collect(Collectors.averagingInt(Rating::getRating));
        kafkaTemplate.send(doctorRatingTopic, AverageRating
            .builder()
            .doctorId(event.getDoctorId())
            .avgRating(avgRating)
            .build());
    }
}
