package com.unobnb.paymentservice.controller;

import com.unobnb.paymentservice.dto.PaymentRequestDto;
import com.unobnb.paymentservice.dto.PaymentResponseDto;
import com.unobnb.paymentservice.dto.PaymentCancelRequestDto;
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
    public PaymentResponseDto cancelPayment(@RequestBody PaymentCancelRequestDto requestDto) {
        return paymentService.cancelPayment(requestDto); // ✅ 같은 타입으로!
    }
}
