package com.example.demo.repository;

import com.example.demo.model.BookedSeat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, Long> {
    @Query("select bs.seat.id from BookedSeat bs where bs.booking.id = :bookingId")
    List<Long> findSeatIdsByBookingId(@Param("bookingId") Long bookingId);
}
