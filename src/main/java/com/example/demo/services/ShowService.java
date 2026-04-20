package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.ShowRequestDto;
import com.example.demo.dto.response.ShowResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.model.Screen;
import com.example.demo.model.Show;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ScreenRepository;
import com.example.demo.repository.ShowRepository;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, ScreenRepository screenRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
    }

    public ShowResponseDto createShow(ShowRequestDto showRequestDto) {
        Movie movie = movieRepository.findById(showRequestDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + showRequestDto.getMovieId()));
        Screen screen = screenRepository.findById(showRequestDto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + showRequestDto.getScreenId()));

        Show show = Show.builder()
                .showDate(showRequestDto.getShowDate())
                .startTime(showRequestDto.getStartTime())
                .endTime(showRequestDto.getEndTime())
                .price(showRequestDto.getPrice())
                .movie(movie)
                .screen(screen)
                .build();

        show = showRepository.save(show);
        return mapToResponseDto(show);
    }

    public ShowResponseDto getShowById(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));

        return mapToResponseDto(show);
    }

    public ShowResponseDto updateShow(Long id, ShowRequestDto showRequestDto) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));
        Movie movie = movieRepository.findById(showRequestDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + showRequestDto.getMovieId()));
        Screen screen = screenRepository.findById(showRequestDto.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found with id: " + showRequestDto.getScreenId()));

        show.setShowDate(showRequestDto.getShowDate());
        show.setStartTime(showRequestDto.getStartTime());
        show.setEndTime(showRequestDto.getEndTime());
        show.setPrice(showRequestDto.getPrice());
        show.setMovie(movie);
        show.setScreen(screen);

        show = showRepository.save(show);
        return mapToResponseDto(show);
    }

    public void deleteShow(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with id: " + id));

        showRepository.delete(show);
    }

    private ShowResponseDto mapToResponseDto(Show show) {
        return ShowResponseDto.builder()
                .id(show.getId())
                .showDate(show.getShowDate())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .price(show.getPrice())
                .movieId(show.getMovie().getId())
                .movieTitle(show.getMovie().getTitle())
                .screenId(show.getScreen().getId())
                .build();
    }
}
