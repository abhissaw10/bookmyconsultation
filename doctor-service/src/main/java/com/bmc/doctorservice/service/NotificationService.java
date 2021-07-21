package com.bmc.doctorservice.service;

import com.bmc.doctorservice.model.Doctor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.bmc.doctorservice.model.DoctorStatus.PENDING;

@Log4j2
@Service
public class NotificationService {

    @Autowired
    SeSEmailVerification emailVerification;

    @Autowired
    KafkaTemplate<String, Doctor> kafkaTemplate;

    @Value("${doctor.registration.notification}")
    private String doctorRegistrationNotificationTopic;

    public void notifyDoctorRegistration(Doctor doctor){
        if(PENDING.value.equals(doctor.getStatus())) {
            try {
                emailVerification.sendVerificationEmail(doctor.getEmailId());
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        kafkaTemplate.send(doctorRegistrationNotificationTopic,doctor);
    }
}
