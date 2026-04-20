package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.TheaterRequestDto;
import com.example.demo.dto.response.TheaterResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Theater;
import com.example.demo.repository.TheaterRepository;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public TheaterResponseDto createTheater(TheaterRequestDto theaterRequestDto) {
        Theater theater = Theater.builder()
                .name(theaterRequestDto.getName())
                .location(theaterRequestDto.getLocation())
                .build();

        theater = theaterRepository.save(theater);
        return mapToResponseDto(theater);
    }

    public TheaterResponseDto getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + id));

        return mapToResponseDto(theater);
    }

    public TheaterResponseDto updateTheater(Long id, TheaterRequestDto theaterRequestDto) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + id));

        theater.setName(theaterRequestDto.getName());
        theater.setLocation(theaterRequestDto.getLocation());

        theater = theaterRepository.save(theater);
        return mapToResponseDto(theater);
    }

    public void deleteTheater(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + id));

        theaterRepository.delete(theater);
    }

    private TheaterResponseDto mapToResponseDto(Theater theater) {
        return TheaterResponseDto.builder()
                .id(theater.getId())
                .name(theater.getName())
                .location(theater.getLocation())
                .build();
    }
}
