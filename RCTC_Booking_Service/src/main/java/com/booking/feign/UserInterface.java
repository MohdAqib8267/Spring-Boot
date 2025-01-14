package com.booking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.dtos.UserResponseDto;
import com.booking.modal.User;


@FeignClient("USER-SERVICE")
public interface UserInterface {
	
	@GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable int id); 
	
}
