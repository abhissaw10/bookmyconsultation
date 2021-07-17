package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.model.PaymentDetails;
import com.bmc.appointmentservice.repository.AppointmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.bmc.appointmentservice.model.AppointmentStatus.*;

@Log4j2
@RequiredArgsConstructor
@Component
public class PaymentListener {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    private ObjectMapper mapper;

    private final NotificationService notificationService;

    @KafkaListener(topics = "${payment.confirmation}", groupId = "${kafka.consumer.payment.groupid}",  containerFactory="paymentKafkaListenerContainerFactory")
    public void listenDoctorRegistration(Message<String> message) throws JsonProcessingException {
        PaymentDetails paymentDetails = mapper.readValue(message.getPayload(),PaymentDetails.class);
        log.info("Payment Details received "+paymentDetails.toString());
        Appointment appointment = null;
        if(paymentDetails.getId()!=null){
            appointment = appointmentRepository.findById(paymentDetails.getAppointmentId()).get();
            appointment.setStatus(Confirmed.name());
            appointmentRepository.save(appointment);
        }else{
            appointment = appointmentRepository.findById(paymentDetails.getAppointmentId()).get();
            appointment.setStatus(PaymentDeclined.name());
            appointmentRepository.save(appointment);
        }
        notificationService.notifyAppointmentConfirmation(appointment);
    }
}
