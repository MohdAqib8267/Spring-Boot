package com.booking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.dtos.BookingRequestDto;
import com.booking.dtos.BookingResponseDto;
import com.booking.dtos.UserResponseDto;
import com.booking.feign.UserInterface;
import com.booking.modal.Booking;
import com.booking.modal.Coach;
import com.booking.modal.Train;
import com.booking.modal.User;
import com.booking.repository.BookingRepository;

@Component

public class BookingServiceImp implements BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private UserInterface userInterface;
	
//	public BookingResponseDto newBooking(@RequestBody Booking bookingDetails) {
//		
//		User user = userInterface.getEmployeeById(bookingDetails.getUser().getId());
//		
//		
//		return null;
//		Train availableTrain = findTrainwithCoaches(bookingDetails.getTrain().getTrainNumber());
//		
//		List<Coach>coaches = availableTrain.getCoaches();
//		Collections.sort(coaches, new Comparator<Coach>() {
//			public int compare(Coach c1, Coach c2) {
//		        return Integer.compare(c1.getAvailableSeats(), c2.getAvailableSeats());
//		    }
//		});
//		//now count available seat for perticular coach type that user gives
//		int countAvailableSeatsForGivenCoachType=0;
//		
//			for(Coach c:coaches) {
//				if(c.getCoachType().equalsIgnoreCase(bookingDetails.getCoachType())) {
//					countAvailableSeatsForGivenCoachType += c.getAvailableSeats();
//				}
//			}
//		
//		if(countAvailableSeatsForGivenCoachType>0) {
//			 throw new RuntimeException("No seats available for "+bookingDetails.getCoachType());
//			 return null;
//		}
//		
//        int ticketsBooked = 0;
//        Map<String, List<Integer>> coachWithSeatNumbers = new HashMap<>();
//        for(Coach c:coaches) {
//        	 if (ticketsBooked >= bookingDetails.getNoOfTickets()) break;
//        	 List<Integer> seatNumbers = new ArrayList<>();
//        	 int availableSeats = c.getAvailableSeats();
//        	 for(int i=(c.getTotalSeats()-availableSeats +1);i<=c.getTotalSeats() && ticketsBooked < bookingDetails.getNoOfTickets();i++) {
//        		 Booking booking = new Booking();
//        		 booking.setUser(bookingDetails.getUser());
//        		 booking.setTrain(bookingDetails.getTrain());
//                 booking.setCoachType(c.getCoachType());
//                 booking.setSeatNumber(i);
//                 booking.setBookingDate(LocalDate.now());
//                 booking.setJourneyDate(bookingDetails.getJourneyDate()); // Example: Use train's departure date
//                 bookingRepository.save(booking);
//                 seatNumbers.add(i);
//                 ticketsBooked++;
              
//                 coach.setAvailableSeats(coach.getAvailableSeats() - 1); //microservice call
//                 coachRepository.save(coach);
//             }
//        	 if (!seatNumbers.isEmpty()) {
//                 coachWithSeatNumbers.put(c.getCoachName(), seatNumbers);
//             }
//        }
//        if (ticketsBooked < bookingDetails.getNoOfTickets()) {
//            throw new RuntimeException("Not enough seats available in the requested coach type.");
//        }
//        return new BookingResponseDto(
//                user.getUsername(),
//                availableTrain.getTrainNumber(),
//                availableTrain.getTrainName(),
//                LocalDate.now(),
//                bookingDetails.getNoOfTickets(),
//                bookingDetails.getJourneyDate(), // Assuming journey date is train's departure date
//                availableTrain.getSource(),
//                availableTrain.getDestination(),
//                availableTrain.getArrivalTime(),
//                availableTrain.getDepartureTime(),
//                coachWithSeatNumbers
//        );
		
		
//		return new BookingResponseDto(myBooking.getUser().getUsername(),myBooking.getTrain().getTrainNumber(),myBooking.getTrain().getTrainNumber(),
//				myBooking.getBookingDate(),myBooking.getNoOfTickets() ,myBooking.getJourneyDate(),myBooking.getTrain().getSource(),myBooking.getTrain().getDestination(),
//				myBooking.getTrain().getArrivalTime(),myBooking.getTrain().getDepartureTime())
//	}
	
	public UserResponseDto getUser(@PathVariable int id) {
		UserResponseDto user = userInterface.getUserById(id);
		
		
		
		return user;
	}
}
