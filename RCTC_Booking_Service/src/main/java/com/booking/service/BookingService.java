package com.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.dtos.BookingRequestDto;
import com.booking.dtos.BookingResponseDto;
import com.booking.dtos.UserResponseDto;
import com.booking.modal.Booking;

@Service
public interface BookingService {
//	public Booking newBooking(@RequestBody Booking bookingDetails);
	public UserResponseDto getUser(@PathVariable int id);
}
