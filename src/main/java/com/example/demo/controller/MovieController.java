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

import com.example.demo.dto.request.MovieRequestDto;
import com.example.demo.dto.response.MovieResponseDto;
import com.example.demo.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@RequestBody MovieRequestDto movieRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(@PathVariable Long id, @RequestBody MovieRequestDto movieRequestDto) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
