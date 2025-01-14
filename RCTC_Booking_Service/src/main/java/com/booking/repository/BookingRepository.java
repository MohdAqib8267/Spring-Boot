package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.modal.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer>{

}
