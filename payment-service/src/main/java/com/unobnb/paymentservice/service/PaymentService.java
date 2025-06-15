package com.unobnb.paymentservice.service;

import com.unobnb.paymentservice.dto.PaymentRequestDto;
import com.unobnb.paymentservice.dto.PaymentResponseDto;
import com.unobnb.paymentservice.dto.PaymentCancelRequestDto;

public interface PaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto requestDto);
    PaymentResponseDto cancelPayment(PaymentCancelRequestDto requestDto); // ✅ 여기 리턴 타입!
}
