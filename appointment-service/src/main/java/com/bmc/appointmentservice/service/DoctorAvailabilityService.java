package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.model.Availability;
import com.bmc.appointmentservice.repository.DoctorAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository repository;

    public void saveDoctorAvailability(Availability availability){
        List<com.bmc.appointmentservice.entity.Availability> availabilityList = new ArrayList<>();
        List<com.bmc.appointmentservice.entity.Availability> existingAvailabilities = repository.findByDoctorIdAndAvailabilityDateIn(availability.getDoctorId(),availability.getAvailabilityMap().keySet().stream().collect(Collectors.toList()));
        repository.deleteAll(existingAvailabilities);//Deleting the existing availabilities for the dates
        availability.getAvailabilityMap().forEach((date,slot) -> {
            availabilityList.addAll(slot.stream().map(s->{
                return com.bmc.appointmentservice.entity.Availability
                    .builder()
                    .doctorId(availability.getDoctorId())
                    .availabilityDate(date)
                    .isBooked(false)
                    .timeSlot(s)
                    .build();
            }).collect(Collectors.toList()));
        });
        repository.saveAll(availabilityList);
    }

    public Availability getDoctorAvailability(String doctorId){
        List<com.bmc.appointmentservice.entity.Availability> availabilities = getAvailabilities(doctorId);
        if(availabilities!=null) {
            return Availability
                .builder()
                .doctorId(doctorId)
                .availabilityMap(getTimeSlots(availabilities))
                .build();
        }
        return null;
    }

    protected List<com.bmc.appointmentservice.entity.Availability> getAvailabilities(String doctorId) {
        List<com.bmc.appointmentservice.entity.Availability> availabilities = repository.findByDoctorIdAndIsBooked(doctorId, false);
        return availabilities;
    }

    protected com.bmc.appointmentservice.entity.Availability updateAvailability(com.bmc.appointmentservice.entity.Availability availability){
        return repository.save(availability);
    }

    private Map<String,List<String>> getTimeSlots(List<com.bmc.appointmentservice.entity.Availability> availabilities){
        Map<String,List<String>> timeSlots = new HashMap<>();
        for(com.bmc.appointmentservice.entity.Availability availability: availabilities){
            List<String> timeSlotList = timeSlots.get(availability.getAvailabilityDate());
            if(timeSlotList==null || timeSlotList.isEmpty()){
                timeSlotList = new ArrayList<>();
            }
            timeSlotList.add(availability.getTimeSlot());
            timeSlots.put(availability.getAvailabilityDate(),timeSlotList);
        }
       return timeSlots;
    }
}
