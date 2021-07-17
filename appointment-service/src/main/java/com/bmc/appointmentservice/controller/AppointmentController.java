package com.bmc.appointmentservice.controller;

import brave.Response;
import com.bmc.appointmentservice.exception.SlotUnavailableException;
import com.bmc.appointmentservice.model.Appointment;
import com.bmc.appointmentservice.model.Prescription;
import com.bmc.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/appointments")
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) throws SlotUnavailableException {
        return ResponseEntity.ok(appointmentService.appointment(appointment));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable String appointmentId) throws SlotUnavailableException {
        return ResponseEntity.ok(appointmentService.getAppointment(appointmentId));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/prescriptions")
    public ResponseEntity prescription(@RequestBody Prescription prescription){
        appointmentService.prescription(prescription);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/appointments")
    public ResponseEntity getAppointmentForUser(@PathVariable("userId") String userId){
        return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
    }
}