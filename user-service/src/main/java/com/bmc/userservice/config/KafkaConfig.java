package com.bmc.userservice.config;


import com.bmc.userservice.model.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrap.address}")
    private String bootStrapAddress;

    public Properties propsMap() throws IOException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootStrapAddress);
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("linger.ms", 0);
        properties.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.springframework.kafka.support.serializer.JsonSerializer");

        properties.put("request.timeout.ms", 30000);
        properties.put("timeout.ms", 30000);
        properties.put("max.in.flight.requests.per.connection", 5);
        properties.put("retry.backoff.ms", 5);
        return properties;
    }

    @Bean
    public Producer<String,User> kafkaProducer() throws IOException {
        return new KafkaProducer<String, User>(propsMap());
    }

}
