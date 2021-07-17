package com.bmc.capstonepaymentservice.service;

import com.bmc.capstonepaymentservice.model.PaymentDetails;
import com.bmc.capstonepaymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    KafkaTemplate<String,PaymentDetails> kafkaTemplate;

    @Value("${payment.confirmation.topic}")
    private String paymentConfirmationTopic;

    public PaymentDetails save(String appointmentId) {
        PaymentDetails paymentDetails = PaymentDetails
            .builder()
            .id(UUID.randomUUID().toString()) // DUMMY PAYMENT SERVICE
            .appointmentId(appointmentId)
            .createDate(new Date())
            .build();
        paymentRepository.save(paymentDetails);
        notify(paymentDetails);
        return paymentDetails;
    }

    private void notify(PaymentDetails paymentDetails){
        kafkaTemplate.send(paymentConfirmationTopic, paymentDetails);
    }
}
