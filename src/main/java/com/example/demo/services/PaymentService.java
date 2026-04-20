package com.example.demo.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.PaymentRequestDto;
import com.example.demo.dto.response.PaymentResponseDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Booking;
import com.example.demo.model.Payment;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        Booking booking = bookingRepository.findById(paymentRequestDto.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + paymentRequestDto.getBookingId()));

        if (!"PENDING_PAYMENT".equals(booking.getStatus())) {
            throw new BadRequestException("Booking must be in PENDING_PAYMENT status to process payment");
        }

        if (paymentRequestDto.getAmount() != booking.getTotalAmount()) {
            throw new BadRequestException("Payment amount must match booking total amount of " + booking.getTotalAmount());
        }

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(paymentRequestDto.getAmount())
                .paymentMethod(paymentRequestDto.getPaymentMethod())
                .paymentStatus("SUCCESS")
                .transactionId(UUID.randomUUID().toString())
                .build();

        payment = paymentRepository.save(payment);

        booking.setStatus("BOOKED");
        bookingRepository.save(booking);

        return mapToResponseDto(payment);
    }

    public PaymentResponseDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        return mapToResponseDto(payment);
    }

    public PaymentResponseDto updatePayment(Long id, PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        Booking booking = bookingRepository.findById(paymentRequestDto.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + paymentRequestDto.getBookingId()));

        payment.setBooking(booking);
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());

        payment = paymentRepository.save(payment);
        return mapToResponseDto(payment);
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        paymentRepository.delete(payment);
    }

    private PaymentResponseDto mapToResponseDto(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .bookingId(payment.getBooking().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .build();
    }
}
