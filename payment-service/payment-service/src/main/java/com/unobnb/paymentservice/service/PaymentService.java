package com.unobnb.paymentservice.service;

import com.unobnb.paymentservice.dto.*;

public interface PaymentService {
    PaymentResponseDto processPayment(PaymentRequestDto requestDto);

    PaymentCancelResponseDto cancelPayment(PaymentCancelRequestDto requestDto); // ✅ 추가
}
