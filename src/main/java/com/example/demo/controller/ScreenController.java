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

import com.example.demo.dto.request.ScreenRequestDto;
import com.example.demo.dto.response.ScreenResponseDto;
import com.example.demo.services.ScreenService;

@RestController
@RequestMapping("/screens")
public class ScreenController {
    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    public ResponseEntity<ScreenResponseDto> createScreen(@RequestBody ScreenRequestDto screenRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(screenService.createScreen(screenRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponseDto> getScreenById(@PathVariable Long id) {
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponseDto> updateScreen(@PathVariable Long id, @RequestBody ScreenRequestDto screenRequestDto) {
        return ResponseEntity.ok(screenService.updateScreen(id, screenRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.noContent().build();
    }
}
