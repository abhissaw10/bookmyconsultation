package com.bmc.appointmentservice.repository;

import com.bmc.appointmentservice.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<Availability,String> {

    public List<Availability> findByDoctorIdAndIsBooked(String doctorId, boolean isBooked);
    public List<Availability> findByDoctorIdAndAvailabilityDateIn(String doctorId, List<String> availabilityDate);
}
