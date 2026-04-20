package com.example.demo.dto.request;

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
public class BookingRequestDto {
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
}
