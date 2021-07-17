package com.bmc.userservice.controller.advice;

import com.bmc.userservice.exception.InvalidInputException;
import com.bmc.userservice.exception.ResourceUnAvailableException;
import com.bmc.userservice.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.bmc.userservice.constants.UserConstants.RESOURCE_UNAVAILABLE;
import static com.bmc.userservice.constants.UserConstants.RESOURCE_UNAVAILABLE_MSG;
import static com.bmc.userservice.util.UserConstants.INVALID_INPUT_ERROR_CODE;
import static com.bmc.userservice.util.UserConstants.INVALID_INPUT_ERROR_MSG;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(ResourceUnAvailableException.class)
    public ResponseEntity<ErrorModel> handleResourceUnavailableException(){
        return new ResponseEntity(ErrorModel
            .builder()
            .errorCode(RESOURCE_UNAVAILABLE)
            .errorMessage(RESOURCE_UNAVAILABLE_MSG)
            .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorModel> handleInvalidInput(InvalidInputException e){
        return new ResponseEntity(ErrorModel
            .builder()
            .errorCode(INVALID_INPUT_ERROR_CODE)
            .errorMessage(INVALID_INPUT_ERROR_MSG)
            .errorFields(e.getAttributeNames())
            .build(), HttpStatus.BAD_REQUEST);
    }
}
