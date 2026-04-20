package com.example.demo.dto.response;

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
public class PaymentResponseDto {
    private Long id;
    private Long bookingId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
}
