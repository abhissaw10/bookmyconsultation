package com.bmc.appointmentservice.repository;

import com.bmc.appointmentservice.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrescriptionRepository extends MongoRepository<Prescription,String> {
}
