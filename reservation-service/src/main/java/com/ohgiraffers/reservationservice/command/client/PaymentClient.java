package com.ohgiraffers.reservationservice.command.client;

import com.ohgiraffers.reservationservice.command.dto.PaymentCancelRequest;
import com.ohgiraffers.reservationservice.command.dto.PaymentDTO;
import com.ohgiraffers.reservationservice.command.dto.PaymentRequest;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import com.ohgiraffers.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", configuration = FeignClientConfig.class)
public interface PaymentClient {

    @PostMapping("/payments/process")
    PaymentDTO processPayment(@RequestBody PaymentRequest request);

    @PostMapping("/payments/cancel")
    PaymentDTO cancelPayment(@RequestBody PaymentCancelRequest request);
}
