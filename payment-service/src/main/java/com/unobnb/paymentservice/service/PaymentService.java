package com.unobnb.paymentservice.service;

import com.unobnb.paymentservice.dto.PaymentRequestDto;
import com.unobnb.paymentservice.dto.PaymentResponseDto;
import com.unobnb.paymentservice.dto.PaymentCancelRequestDto;

public interface PaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto requestDto);
    PaymentResponseDto cancelPayment(PaymentCancelRequestDto requestDto);
    PaymentResponseDto getPayment(Long paymentId); // ✅ 결제 조회 추가
}
