package com.bmc.capstonepaymentservice.repository;

import com.bmc.capstonepaymentservice.model.PaymentDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentDetails,String> {
}
