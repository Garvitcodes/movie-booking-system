package com.example.demo.dto.request;

import com.example.demo.model.SeatType;
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
public class SeatRequestDto {
    private String seatNumber;
    private SeatType seatType;
    private boolean available;
    private Long screenId;
}
