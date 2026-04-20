package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
public class BookingResponseDto {
    private Long id;
    private LocalDateTime bookingTime;
    private double totalAmount;
    private String status;
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
}
