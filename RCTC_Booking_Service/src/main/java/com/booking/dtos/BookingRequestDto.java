package com.booking.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingRequestDto {
	private int userId;
	private int trainNumber;
	private LocalDate bookingDate;
	private int noOfTickets;
	
	public BookingRequestDto() {}
    public BookingRequestDto(int userId, int trainNumber, int noOfTickets, LocalDate bookingDate) {
        this.userId = userId;
        this.trainNumber = trainNumber;
        this.noOfTickets = noOfTickets;
        this.bookingDate = bookingDate;
    }
}
