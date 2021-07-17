package com.bmc.notificationservice.config;

import com.bmc.notificationservice.freemarker.AppointmentConfirmationEmail;
import com.bmc.notificationservice.freemarker.PrescriptionEmail;
import com.bmc.notificationservice.model.Appointment;
import com.bmc.notificationservice.model.Doctor;
import com.bmc.notificationservice.model.Prescription;
import com.bmc.notificationservice.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class NotificationKafkaListener {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PrescriptionEmail prescriptionEmail;

    @Autowired
    private AppointmentConfirmationEmail appointmentEmail;

    @KafkaListener(topics = "${doctor.registration.notification}", groupId = "${kafka.consumer.doctor.registration.groupid}",  containerFactory="doctorKafkaListenerContainerFactory")
    public void listenDoctorRegistration(Message<Doctor> message) {
        System.out.println("Received Message in group: " + message.getPayload());
    }

    @KafkaListener(topics = "${appointment.notification}", groupId = "${kafka.consumer.appointment.groupid}",  containerFactory="appointmentKafkaListenerContainerFactory")
    public void listenAppointmentConfirmation(Message<String> message) throws IOException, MessagingException, TemplateException {

        Appointment appointment = mapper.readValue(message.getPayload(),Appointment.class);
        System.out.println("Received Message in group: " + message.getPayload());
        appointmentEmail.sendEmail(appointment);
    }

    @KafkaListener(topics = "${user.registration.notification}", groupId = "${kafka.consumer.appointment.groupid}",  containerFactory="appointmentKafkaListenerContainerFactory")
    public void listenUserRegistration(Message<String> message) throws JsonProcessingException {

        User user = mapper.readValue(message.getPayload(),User.class);
        System.out.println("Received Message in User group: " + message.getPayload());
    }

    @KafkaListener(topics = "${prescription_notification}", groupId = "${kafka.consumer.appointment.groupid}",  containerFactory="appointmentKafkaListenerContainerFactory")
    public void listenPrescription(Message<String> message) throws IOException, MessagingException, TemplateException {

        Prescription prescription = mapper.readValue(message.getPayload(), Prescription.class);
        prescriptionEmail.sendEmail(prescription);
        System.out.println("Received Message in prescription group: " + message.getPayload());
    }

}
