package com.bmc.ratingservice.service;

import com.bmc.ratingservice.event.RatingAddEvent;
import com.bmc.ratingservice.model.Rating;
import com.bmc.ratingservice.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RatingsService {

    private final ApplicationEventPublisher publisher;

    private final RatingsRepository ratingsRepository;

    public void submitRatings(Rating rating) {
        rating.setId(UUID.randomUUID().toString());
        ratingsRepository.save(rating);
        publisher.publishEvent(new RatingAddEvent(rating.getDoctorId()));
    }
}
