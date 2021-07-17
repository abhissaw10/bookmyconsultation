package com.bmc.appointmentservice.config;

import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.model.PaymentDetails;
import com.bmc.appointmentservice.model.Prescription;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrap.address}")
    private String bootStrapAddress;

    @Value(value = "${kafka.consumer.payment.groupid}")
    private String paymentConfirmationGroupId;


    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<String, Appointment> producerFactory(){
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(configProps,new StringSerializer(),jsonSerializer);
    }

    @Bean
    public ProducerFactory<String, Prescription> producerFactoryPrescription(){
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        JsonSerializer jsonSerializer = new JsonSerializer();
        jsonSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(configProps,new StringSerializer(),jsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, Appointment> kafkaTemplate(){
        return new KafkaTemplate<String, Appointment>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, Prescription> kafkaTemplatePrescription(){
        return new KafkaTemplate<String, Prescription>(producerFactoryPrescription());
    }

    @Bean
    public ConsumerFactory<String, PaymentDetails> paymentConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootStrapAddress);
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            paymentConfirmationGroupId);
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentDetails>
    paymentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PaymentDetails> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentConsumerFactory());
        return factory;
    }
}
