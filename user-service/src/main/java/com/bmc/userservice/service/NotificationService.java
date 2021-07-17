package com.bmc.userservice.service;

import com.bmc.userservice.model.User;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.model.SesException;

@Service
@Log4j2
public class NotificationService {

    @Autowired
    private SeSEmailVerification emailVerification;

    @Autowired
    Producer<String,User> producer;

    @Value("${user.registration.notification}")
    private String userRegistrationTopic;

    public void notifyUser(User user){
        log.info(user);

        try {
            producer.send(new ProducerRecord<>(userRegistrationTopic,null,user));
            emailVerification.sendVerificationEmail(user.getEmailId());
        }catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
