package com.example.RCTC_SB.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.RCTC_SB.dto.UserResponseDto;



@FeignClient("RCTC-USER-SERVICE")
public interface UserInterface {

	@GetMapping("/api/user")
	public ResponseEntity<List<UserResponseDto>> getAllUsers();
}
