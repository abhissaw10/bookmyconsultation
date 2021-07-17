package com.bmc.appointmentservice.service;

import com.bmc.appointmentservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="user-service", url = "${user-service-url}")
public interface UserClient {
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String authorization,@PathVariable String userId);
}
