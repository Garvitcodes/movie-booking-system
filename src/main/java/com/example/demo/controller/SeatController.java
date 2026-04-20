package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.SeatRequestDto;
import com.example.demo.dto.response.SeatResponseDto;
import com.example.demo.services.SeatService;

@RestController
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<SeatResponseDto> createSeat(@RequestBody SeatRequestDto seatRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.createSeat(seatRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponseDto> getSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatResponseDto> updateSeat(@PathVariable Long id, @RequestBody SeatRequestDto seatRequestDto) {
        return ResponseEntity.ok(seatService.updateSeat(id, seatRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
