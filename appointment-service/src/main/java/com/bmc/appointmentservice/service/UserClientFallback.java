package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.model.User;
import org.springframework.http.ResponseEntity;

public class UserClientFallback implements UserClient{
    @Override
    public ResponseEntity<User> getUser(String authorization, String userId) {
        return null;
    }
}
