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

import com.example.demo.dto.request.TheaterRequestDto;
import com.example.demo.dto.response.TheaterResponseDto;
import com.example.demo.services.TheaterService;

@RestController
@RequestMapping("/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    public ResponseEntity<TheaterResponseDto> createTheater(@RequestBody TheaterRequestDto theaterRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterService.createTheater(theaterRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponseDto> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheaterResponseDto> updateTheater(@PathVariable Long id, @RequestBody TheaterRequestDto theaterRequestDto) {
        return ResponseEntity.ok(theaterService.updateTheater(id, theaterRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
