package com.unobnb.paymentservice.controller;

import com.unobnb.paymentservice.dto.*;
import com.unobnb.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public PaymentResponseDto processPayment(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.processPayment(requestDto);
    }

    @PostMapping("/cancel")
    public PaymentCancelResponseDto cancelPayment(@RequestBody PaymentCancelRequestDto requestDto) {
        return paymentService.cancelPayment(requestDto);
    }
}
