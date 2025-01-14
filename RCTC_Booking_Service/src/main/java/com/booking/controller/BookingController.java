package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dtos.BookingRequestDto;
import com.booking.dtos.BookingResponseDto;
import com.booking.dtos.UserResponseDto;
import com.booking.modal.Booking;
import com.booking.service.BookingService;


@RestController
@RequestMapping("/api/booking")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingDetails){
		return null;
	}
	
	@PostMapping("/find/{id}")
	public UserResponseDto getUser(@PathVariable int id){
		return bookingService.getUser(id);
	}
}
