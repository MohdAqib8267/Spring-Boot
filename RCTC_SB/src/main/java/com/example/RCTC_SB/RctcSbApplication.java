package com.example.RCTC_SB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RctcSbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RctcSbApplication.class, args);
		
		System.out.println("hi");
	}

}
