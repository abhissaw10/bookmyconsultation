package com.bmc.appointmentservice.controller.advice;

import com.bmc.appointmentservice.controller.model.ErrorModel;
import com.bmc.appointmentservice.exception.ResourceUnAvailableException;
import com.bmc.appointmentservice.exception.SlotUnavailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppointmentControllerAdvice {

    @ExceptionHandler(SlotUnavailableException.class)
    public ResponseEntity handleSlotUnavailableException(){
        return ResponseEntity
            .badRequest()
            .body(ErrorModel
                .builder()
                .errorCode("ERR_SLOT_UNAVAILABLE")
                .errorMessage("Either the slot is already booked or not available")
                .build());
    }

    @ExceptionHandler(ResourceUnAvailableException.class)
    public ResponseEntity handleResourceUnavailableException(){
        return ResponseEntity
            .badRequest()
            .body(ErrorModel
                .builder()
                .errorCode("ERR_INVALID_APPOINTMENT")
                .errorMessage("The appointment id is invalid")
                .build());
    }

}
