package com.example.demo.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDto {
    private Long id;
    private String title;
    private String genre;
    private int duration;
    private LocalDate releaseDate;
    private double rating;
}
