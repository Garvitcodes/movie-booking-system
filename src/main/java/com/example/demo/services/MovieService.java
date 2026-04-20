package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.MovieRequestDto;
import com.example.demo.dto.response.MovieResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieResponseDto createMovie(MovieRequestDto movieRequestDto) {
        Movie movie = Movie.builder()
                .title(movieRequestDto.getTitle())
                .genre(movieRequestDto.getGenre())
                .duration(movieRequestDto.getDuration())
                .releaseDate(movieRequestDto.getReleaseDate())
                .rating(movieRequestDto.getRating())
                .build();

        movie = movieRepository.save(movie);
        return mapToResponseDto(movie);
    }

    public MovieResponseDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        return mapToResponseDto(movie);
    }

    public MovieResponseDto updateMovie(Long id, MovieRequestDto movieRequestDto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        movie.setTitle(movieRequestDto.getTitle());
        movie.setGenre(movieRequestDto.getGenre());
        movie.setDuration(movieRequestDto.getDuration());
        movie.setReleaseDate(movieRequestDto.getReleaseDate());
        movie.setRating(movieRequestDto.getRating());

        movie = movieRepository.save(movie);
        return mapToResponseDto(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        movieRepository.delete(movie);
    }

    private MovieResponseDto mapToResponseDto(Movie movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .build();
    }
}
