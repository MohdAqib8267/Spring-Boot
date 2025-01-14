package com.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.booking.feign","com.booking.controller","com.booking.dtos","com.booking.service",
		"com.booking.modal","com.booking.repository"})
public class RctcBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RctcBookingServiceApplication.class, args);
	}

}
