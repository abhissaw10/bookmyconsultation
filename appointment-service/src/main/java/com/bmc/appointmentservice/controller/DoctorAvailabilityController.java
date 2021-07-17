package com.bmc.appointmentservice.controller;

import brave.Response;
import com.bmc.appointmentservice.exception.AvailabilityUnAvailableException;
import com.bmc.appointmentservice.model.Availability;
import com.bmc.appointmentservice.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DoctorAvailabilityController {
    private final DoctorAvailabilityService availabilityService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/doctor/{id}/availability")
    public ResponseEntity<String> saveDoctorAvailability(@PathVariable String id, @RequestBody Availability availabilityRequest) throws AvailabilityUnAvailableException {
        availabilityRequest.setDoctorId(id);
        availabilityService.saveDoctorAvailability(availabilityRequest);
    return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/doctor/{id}/availability")
    public ResponseEntity<Availability> getDoctorAvailability(@PathVariable String id){
        return ResponseEntity.ok(availabilityService.getDoctorAvailability(id));
    }
}
