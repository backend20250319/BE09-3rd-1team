package com.unobnb.paymentservice.service;

import com.unobnb.paymentservice.dto.*;
import com.unobnb.paymentservice.entity.Payment;
import com.unobnb.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto requestDto) {

        int status;
        if (requestDto.getPrice() <= 0) {
            status = 0;
        } else if (requestDto.getPrice() > 500_000) {
            status = 0;
        } else {
            status = 1;
        }

        // ✅ 실패면 DB에 저장 안 하고 바로 반환!
        if (status == 0) {
            PaymentResponseDto response = new PaymentResponseDto();
            response.setStatus(0);
            response.setPaymentId(null);
            response.setPaymentDate(null);
            return response;
        }

        // 성공 또는 취소만 저장
        Payment payment = new Payment();
        payment.setPrice(requestDto.getPrice());
        payment.setStatus(status);

        if (status == 1) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponseDto response = new PaymentResponseDto();
        response.setStatus(savedPayment.getStatus());
        response.setPaymentId(savedPayment.getId());
        response.setPaymentDate(savedPayment.getPaymentDate());

        return response;
    }

    @Override
    public PaymentCancelResponseDto cancelPayment(PaymentCancelRequestDto requestDto) {
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

        PaymentCancelResponseDto response = new PaymentCancelResponseDto();
        response.setPaymentId(payment.getId());
        response.setStatus(payment.getStatus());
        response.setPaymentDate(payment.getPaymentDate());
        response.setCancelDate(payment.getCancelDate());

        return response;
    }
}
