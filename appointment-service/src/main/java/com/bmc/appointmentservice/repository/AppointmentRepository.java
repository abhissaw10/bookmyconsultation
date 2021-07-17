package com.bmc.appointmentservice.repository;

import com.bmc.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,String> {
    public List<Appointment> findByUserId(String userId);

}
