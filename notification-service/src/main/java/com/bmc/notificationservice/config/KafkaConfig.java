package com.bmc.notificationservice.config;

import com.bmc.notificationservice.model.Appointment;
import com.bmc.notificationservice.model.Doctor;
import com.bmc.notificationservice.model.Prescription;
import com.bmc.notificationservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrap.address}")
    private String bootStrapAddress;

    @Value(value = "${kafka.consumer.doctor.registration.groupid}")
    private String doctorRegistrationGroupId;

    @Value(value = "${kafka.consumer.appointment.groupid}")
    private String appointmentConfirmationGroupId;


    @Bean
    public ConsumerFactory<String, Doctor> doctorConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootStrapAddress);
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            doctorRegistrationGroupId);
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            JsonDeserializer.class);
        JsonDeserializer jsonDeserializer = new JsonDeserializer<>(Doctor.class, false);
        jsonDeserializer.trustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Doctor>
    doctorKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Doctor> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(doctorConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Appointment> appointmentConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootStrapAddress);
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            appointmentConfirmationGroupId);
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        //StringDeserializer<Appointment> jsonDeserializer = new StringDeserializer<>(Appointment.class);
        return new DefaultKafkaConsumerFactory<>(props);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Appointment>
    appointmentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Appointment> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(appointmentConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, User> userConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootStrapAddress);
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            appointmentConfirmationGroupId);
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        //StringDeserializer<Appointment> jsonDeserializer = new StringDeserializer<>(Appointment.class);
        return new DefaultKafkaConsumerFactory<>(props);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User>
    userKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, User> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Prescription> prescriptionConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootStrapAddress);
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            appointmentConfirmationGroupId);
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Prescription>
    prescriptionKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Prescription> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(prescriptionConsumerFactory());
        return factory;
    }

    @Bean
    ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    /*public Properties configMap(){
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", bootStrapAddress);
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    @Bean
    public KafkaConsumer<String,String> userKafkaConsumer(){
        return new KafkaConsumer(configMap());
    }
*/

}
