package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.SeatRequestDto;
import com.example.demo.dto.response.SeatResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Screen;
import com.example.demo.model.Seat;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.repository.SeatRepository;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatService(SeatRepository seatRepository, ScreenRepository screenRepository) {
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }

    public SeatResponseDto createSeat(SeatRequestDto seatRequestDto) {
        Screen screen = screenRepository.findById(seatRequestDto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + seatRequestDto.getScreenId()));

        Seat seat = Seat.builder()
                .seatNumber(seatRequestDto.getSeatNumber())
                .seatType(seatRequestDto.getSeatType())
                .isAvailable(seatRequestDto.isAvailable())
                .screen(screen)
                .build();

        seat = seatRepository.save(seat);
        return mapToResponseDto(seat);
    }

    public SeatResponseDto getSeatById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + id));

        return mapToResponseDto(seat);
    }

    public SeatResponseDto updateSeat(Long id, SeatRequestDto seatRequestDto) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + id));
        Screen screen = screenRepository.findById(seatRequestDto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + seatRequestDto.getScreenId()));

        seat.setSeatNumber(seatRequestDto.getSeatNumber());
        seat.setSeatType(seatRequestDto.getSeatType());
        seat.setAvailable(seatRequestDto.isAvailable());
        seat.setScreen(screen);

        seat = seatRepository.save(seat);
        return mapToResponseDto(seat);
    }

    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + id));

        seatRepository.delete(seat);
    }

    private SeatResponseDto mapToResponseDto(Seat seat) {
        return SeatResponseDto.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .available(seat.isAvailable())
                .screenId(seat.getScreen().getId())
                .build();
    }
}
