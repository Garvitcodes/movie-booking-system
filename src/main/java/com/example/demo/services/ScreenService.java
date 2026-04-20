package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ScreenRequestDto;
import com.example.demo.dto.response.ScreenResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Screen;
import com.example.demo.model.Theater;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.repository.TheaterRepository;

@Service
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    public ScreenService(ScreenRepository screenRepository, TheaterRepository theaterRepository) {
        this.screenRepository = screenRepository;
        this.theaterRepository = theaterRepository;
    }

    public ScreenResponseDto createScreen(ScreenRequestDto screenRequestDto) {
        Theater theater = theaterRepository.findById(screenRequestDto.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + screenRequestDto.getTheaterId()));

        Screen screen = Screen.builder()
                .name(screenRequestDto.getName())
                .capacity(screenRequestDto.getCapacity())
                .theater(theater)
                .build();

        screen = screenRepository.save(screen);
        return mapToResponseDto(screen);
    }

    public ScreenResponseDto getScreenById(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + id));

        return mapToResponseDto(screen);
    }

    public ScreenResponseDto updateScreen(Long id, ScreenRequestDto screenRequestDto) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + id));
        Theater theater = theaterRepository.findById(screenRequestDto.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found with id: " + screenRequestDto.getTheaterId()));

        screen.setName(screenRequestDto.getName());
        screen.setCapacity(screenRequestDto.getCapacity());
        screen.setTheater(theater);

        screen = screenRepository.save(screen);
        return mapToResponseDto(screen);
    }

    public void deleteScreen(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + id));

        screenRepository.delete(screen);
    }

    private ScreenResponseDto mapToResponseDto(Screen screen) {
        return ScreenResponseDto.builder()
                .id(screen.getId())
                .name(screen.getName())
                .capacity(screen.getCapacity())
                .theaterId(screen.getTheater().getId())
                .build();
    }
}
