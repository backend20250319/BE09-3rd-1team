package com.ohgiraffers.reservationservice.command.client;

import com.ohgiraffers.reservationservice.command.dto.PaymentCancelRequest;
import com.ohgiraffers.reservationservice.command.dto.PaymentDTO;
import com.ohgiraffers.reservationservice.command.dto.PaymentRequest;
import com.ohgiraffers.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// gateway 통해서 접근
@FeignClient(name = "payment-service", url = "http://localhost:8000/api/v1/payment-service", configuration = FeignClientConfig.class)
public interface PaymentClient {

    @PostMapping("/payments/process")
    PaymentDTO processPayment(@RequestBody PaymentRequest request);

    @PostMapping("/payments/cancel")
    PaymentDTO cancelPayment(@RequestBody PaymentCancelRequest request);
}
