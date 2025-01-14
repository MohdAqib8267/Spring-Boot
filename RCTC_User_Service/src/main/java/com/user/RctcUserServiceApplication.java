package com.user;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients
@ComponentScan(basePackages = {"com.user.controller","com.user.dtos","com.user.modal","com.user.repository","com.user.service"})
public class RctcUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RctcUserServiceApplication.class, args);
	}

}
