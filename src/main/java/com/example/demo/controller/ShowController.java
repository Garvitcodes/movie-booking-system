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

import com.example.demo.dto.request.ShowRequestDto;
import com.example.demo.dto.response.ShowResponseDto;
import com.example.demo.services.ShowService;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<ShowResponseDto> createShow(@RequestBody ShowRequestDto showRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.createShow(showRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponseDto> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowResponseDto> updateShow(@PathVariable Long id, @RequestBody ShowRequestDto showRequestDto) {
        return ResponseEntity.ok(showService.updateShow(id, showRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}
