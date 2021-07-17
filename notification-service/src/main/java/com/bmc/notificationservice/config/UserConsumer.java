package com.bmc.notificationservice.config;

import com.bmc.notificationservice.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;

@Component
public class UserConsumer {

    @Value("${user.registration.notification}")
    private static String userRegistration;
    @Autowired
    static KafkaConsumer<String, String> consumer;
    @Autowired
    static ObjectMapper mapper;

    public static void pollUserTopic(){
        consumer.subscribe(Arrays.asList(userRegistration));

        Set<String> subscribedTopics = consumer.subscription();
        subscribedTopics.stream().forEach(System.out::println);

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    mapper.readValue(record.value(), User.class);
                }
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

}
