package com.bmc.ratingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RatingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}
}
