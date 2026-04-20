package com.example.demo.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class ShowRequestDto {
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    private Long movieId;
    private Long screenId;
}
