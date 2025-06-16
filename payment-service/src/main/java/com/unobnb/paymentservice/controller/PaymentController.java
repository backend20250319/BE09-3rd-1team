package com.unobnb.paymentservice.controller;

import com.unobnb.paymentservice.dto.PaymentCancelRequestDto;
import com.unobnb.paymentservice.dto.PaymentRequestDto;
import com.unobnb.paymentservice.dto.PaymentResponseDto;
import com.unobnb.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment API")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "결제 생성", description = "결제 생성")
    @PostMapping("/process")
    public PaymentResponseDto processPayment(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.processPayment(requestDto);
    }

    @Operation(summary = "결제 삭제", description = "결제 생성")
    @PostMapping("/cancel")
    public PaymentResponseDto cancelPayment(@RequestBody PaymentCancelRequestDto requestDto) {
        return paymentService.cancelPayment(requestDto);
    }

    @Operation(summary = "결제 조회", description = "결제 조회")
    @GetMapping("/{paymentId}")
    public PaymentResponseDto getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}
