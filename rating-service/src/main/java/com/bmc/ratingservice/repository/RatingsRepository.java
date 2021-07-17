package com.bmc.ratingservice.repository;

import com.bmc.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingsRepository extends MongoRepository<Rating,String> {
    public List<Rating> findByDoctorId(String doctorId);
}
