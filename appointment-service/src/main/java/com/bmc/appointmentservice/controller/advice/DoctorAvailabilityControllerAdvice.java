package com.bmc.appointmentservice.controller.advice;

import com.bmc.appointmentservice.controller.model.ErrorModel;
import com.bmc.appointmentservice.exception.AvailabilityUnAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DoctorAvailabilityControllerAdvice {

    @ExceptionHandler(AvailabilityUnAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorModel> handleAvailabilityUnavailableException(){
        return ResponseEntity.badRequest().body(ErrorModel
            .builder()
            .errorCode("EMPTY_AVAILABILITY_MAP")
            .errorMessage("Availability not passed in the request")
            .build());
    }

}
