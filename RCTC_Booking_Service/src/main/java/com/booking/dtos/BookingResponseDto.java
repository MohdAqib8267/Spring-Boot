package com.booking.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BookingResponseDto {
	private String username;
	private int trainNumber;
	private String trainName;
	private LocalDate bookingDate;
	private int noOfTickets;
	private LocalDate journeyDate;
	private String source;
	private String destination;
	private String arrivalTime;
	private String departureTime;
//	private String coachType;
//	private List<Integer> seatNumber;
	private Map<String, List<Integer>> coachWithSeatNumber;  //coach-->array of seatNumbers
	
	public Map<String, List<Integer>> getCoachWithSeatNumber() {
		return coachWithSeatNumber;
	}

	public void setCoachWithSeatNumber(Map<String, List<Integer>> coachWithSeatNumber) {
		this.coachWithSeatNumber = coachWithSeatNumber;
	}

	public BookingResponseDto() {
		
	}

	public BookingResponseDto(String username, int trainNumber, String trainName, LocalDate bookingDate,
			int noOfTickets, LocalDate journeyDate,String source,String destination,String arrivalTime,String departureTime,Map<String, List<Integer>> coachWithSeatNumber) {
		
		this.username = username;
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.bookingDate = bookingDate;
		this.noOfTickets = noOfTickets;
		this.journeyDate = journeyDate;
		this.source=source;
		this.destination=destination;
		this.coachWithSeatNumber = coachWithSeatNumber;
		this.arrivalTime=arrivalTime;
		this.departureTime=departureTime;
	}
	
	
}	
