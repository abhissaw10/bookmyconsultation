package com.bmc.ratingservice.controller;

import com.bmc.ratingservice.model.Rating;
import com.bmc.ratingservice.service.RatingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RatingsController {

    private final RatingsService ratingsService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/ratings")
    public ResponseEntity submitRatings(@RequestBody Rating rating){
        ratingsService.submitRatings(rating);
        return ResponseEntity.ok().build();
    }
}
