package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.entity.Availability;
import com.bmc.appointmentservice.exception.PaymentPendingException;
import com.bmc.appointmentservice.exception.ResourceUnAvailableException;
import com.bmc.appointmentservice.exception.SlotUnavailableException;
import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.model.AppointmentStatus;
import com.bmc.appointmentservice.model.Prescription;
import com.bmc.appointmentservice.model.User;
import com.bmc.appointmentservice.repository.AppointmentRepository;
import com.bmc.appointmentservice.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bmc.appointmentservice.model.AppointmentStatus.PendingPayment;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorAvailabilityService availabilityService;

    private final NotificationService notificationService;

    private final PrescriptionRepository prescriptionRepository;

    private final UserClient userClient;

    public String appointment(Appointment appointment) throws SlotUnavailableException {

        List<Availability> availabilities = availabilityService.getAvailabilities(appointment.getDoctorId());
        boolean slotFound = false;
        for(Availability availability: availabilities){
            if(availability.getAvailabilityDate().equals(appointment.getAppointmentDate())
            && availability.getTimeSlot().equals(appointment.getTimeSlot())) {
                if (!availability.isBooked()) {
                    availability.setBooked(true);
                    availabilityService.updateAvailability(availability);
                } else {
                    throw new SlotUnavailableException();
                }
                slotFound=true;
                break;
            }
        }
        if(slotFound) {
            appointment.setAppointmentId(UUID.randomUUID().toString());
            appointment.setCreatedDate(LocalDateTime.now().toString());
            appointment.setStatus(PendingPayment.name());
            appointmentRepository.save(appointment);
            notify(appointment);
        }else{
            throw new SlotUnavailableException();
        }
        return appointment.getAppointmentId();
    }

    public void prescription(Prescription prescription) throws PaymentPendingException{
        prescription.setId(UUID.randomUUID().toString());
        if(appointmentRepository.getById(prescription.getAppointmentId()).getStatus().equals(AppointmentStatus.Confirmed.name())) {
            prescriptionRepository.save(prescription);
            notify(prescription);
        }else{
            throw new PaymentPendingException();
        }
    }

    private void notify(Appointment appointment){
        User user = getUser(appointment.getUserId());
        if(user!=null) {
            appointment.setUserEmailId(user.getEmailId());
            appointment.setUserName(user.getFirstName() + " " + user.getLastName());
            notificationService.notifyAppointmentConfirmation(appointment);
        }

    }

    private void notify(Prescription prescription){
        User user = getUser(prescription.getUserId());
        if(user!=null) {
            prescription.setUserEmailId(user.getEmailId());
            prescription.setPatientName(user.getFirstName() + user.getLastName());
            notificationService.notifyPrescription(prescription);
        }
    }

    private User getUser(String userId){
        ResponseEntity<User> userEntity = userClient.getUser(getAuthorizationToken(),userId);
        if(userEntity!=null && userEntity.getStatusCode().is2xxSuccessful()){
            return userEntity.getBody();
        }
        log.error("User Not Found having Id "+userId);
        return null;
    }

    private String getAuthorizationToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getCredentials().toString();
    }

    public Appointment getAppointment(String appointmentId) {
        return Optional.ofNullable(appointmentRepository.findById(appointmentId))
            .get()
            .orElseThrow(ResourceUnAvailableException::new);
    }

    public List<Appointment> getAppointmentsForUser(String userId){
        return appointmentRepository.findByUserId(userId);
    }
}
