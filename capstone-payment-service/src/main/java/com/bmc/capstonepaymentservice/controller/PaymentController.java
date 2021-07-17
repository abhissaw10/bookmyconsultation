package com.bmc.capstonepaymentservice.controller;

import com.bmc.capstonepaymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/payments")
    public ResponseEntity payment(@RequestParam("appointmentId") String appointmentId){
        return ResponseEntity.ok(paymentService.save(appointmentId));
    }
}
