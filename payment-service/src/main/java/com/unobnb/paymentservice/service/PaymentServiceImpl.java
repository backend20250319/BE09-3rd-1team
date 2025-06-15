package com.unobnb.paymentservice.service;

import com.unobnb.paymentservice.dto.PaymentRequestDto;
import com.unobnb.paymentservice.dto.PaymentResponseDto;
import com.unobnb.paymentservice.dto.PaymentCancelRequestDto;
import com.unobnb.paymentservice.entity.Payment;
import com.unobnb.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto requestDto) {
        int statusCode;
        String statusText;

        if (requestDto.getPrice() <= 0) {
            statusCode = 0;
            statusText = "FAILED";

            PaymentResponseDto response = new PaymentResponseDto();
            response.setPaymentId(null);
            response.setPaymentNo(null);
            response.setPrice(requestDto.getPrice());
            response.setApprovedAt(null);
            response.setCancelledAt(null);
            response.setStatus(statusText);

            return response;
        } else if (requestDto.getPrice() > 1_000_000) {
            statusCode = 0; // 실패 처리로 통일
            statusText = "FAILED";

            PaymentResponseDto response = new PaymentResponseDto();
            response.setPaymentId(null);
            response.setPaymentNo(null);
            response.setPrice(requestDto.getPrice());
            response.setApprovedAt(null);
            response.setCancelledAt(null);
            response.setStatus(statusText);

            return response;
        } else {
            statusCode = 1;
            statusText = "SUCCESS";
        }

        Payment payment = new Payment();
        payment.setPrice(requestDto.getPrice());
        payment.setStatus(statusCode);
        payment.setPaymentNo(UUID.randomUUID().toString());

        if (statusCode == 1) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponseDto response = new PaymentResponseDto();
        response.setPaymentId(savedPayment.getId());
        response.setPaymentNo(savedPayment.getPaymentNo());
        response.setPrice(savedPayment.getPrice());
        response.setApprovedAt(savedPayment.getPaymentDate());
        response.setCancelledAt(savedPayment.getCancelDate());
        response.setStatus(statusText);

        return response;
    }

    @Override
    public PaymentResponseDto cancelPayment(PaymentCancelRequestDto requestDto) {
        Optional<Payment> optionalPayment = paymentRepository.findById(requestDto.getPaymentId());

        if (optionalPayment.isEmpty()) {
            throw new RuntimeException("해당 결제를 찾을 수 없습니다: ID = " + requestDto.getPaymentId());
        }

        Payment payment = optionalPayment.get();

        if (payment.getStatus() == 1) {
            payment.setStatus(2);
            payment.setCancelDate(LocalDateTime.now());
            paymentRepository.save(payment);
        }

        PaymentResponseDto response = new PaymentResponseDto();
        response.setPaymentId(payment.getId());
        response.setPaymentNo(payment.getPaymentNo());
        response.setPrice(payment.getPrice());
        response.setApprovedAt(payment.getPaymentDate());
        response.setCancelledAt(payment.getCancelDate());
        response.setStatus("CANCELLED");

        return response;
    }
}
