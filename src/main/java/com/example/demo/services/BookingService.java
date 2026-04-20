package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.BookingRequestDto;
import com.example.demo.dto.response.BookingResponseDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BookedSeat;
import com.example.demo.model.Booking;
import com.example.demo.model.Seat;
import com.example.demo.model.Show;
import com.example.demo.model.User;
import com.example.demo.repository.BookedSeatRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookedSeatRepository bookedSeatRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            ShowRepository showRepository,
            SeatRepository seatRepository,
            BookedSeatRepository bookedSeatRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookedSeatRepository = bookedSeatRepository;
    }

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        User user = userRepository.findById(bookingRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + bookingRequestDto.getUserId()));
        Show show = showRepository.findById(bookingRequestDto.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + bookingRequestDto.getShowId()));

        double totalAmount = show.getPrice() * bookingRequestDto.getSeatIds().size();

        Booking booking = Booking.builder()
                .bookingTime(LocalDateTime.now())
                .totalAmount(totalAmount)
                .status("PENDING_PAYMENT")
                .user(user)
                .show(show)
                .build();

        booking = bookingRepository.save(booking);

        for (Long seatId : bookingRequestDto.getSeatIds()) {
            Seat seat = seatRepository.findByIdForUpdate(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + seatId));

            if (!seat.isAvailable()) {
                throw new BadRequestException("Seat with id: " + seatId + " is already booked");
            }

            seat.setAvailable(false);
            seatRepository.save(seat);

            BookedSeat bookedSeat = BookedSeat.builder()
                    .booking(booking)
                    .seat(seat)
                    .build();
            bookedSeatRepository.save(bookedSeat);
        }

        return mapToResponseDto(booking, bookingRequestDto.getSeatIds());
    }

    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        List<Long> seatIds = bookedSeatRepository.findSeatIdsByBookingId(id);

        return mapToResponseDto(booking, seatIds);
    }

    public BookingResponseDto updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        List<Long> seatIds = bookedSeatRepository.findSeatIdsByBookingId(id);

        booking.setStatus(status);
        booking = bookingRepository.save(booking);

        return mapToResponseDto(booking, seatIds);
    }

    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        bookingRepository.delete(booking);
    }

    private BookingResponseDto mapToResponseDto(Booking booking, List<Long> seatIds) {
        return BookingResponseDto.builder()
                .id(booking.getId())
                .bookingTime(booking.getBookingTime())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .userId(booking.getUser().getId())
                .showId(booking.getShow().getId())
                .seatIds(seatIds)
                .build();
    }
}
