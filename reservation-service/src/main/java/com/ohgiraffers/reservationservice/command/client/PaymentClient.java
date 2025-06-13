package com.ohgiraffers.reservationservice.command.client;

import com.ohgiraffers.reservationservice.command.dto.PaymentDTO;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import com.ohgiraffers.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", configuration = FeignClientConfig.class)
public interface PaymentClient {

    @PostMapping("/payments")
    ApiResponse<PaymentDTO> createPayment(@RequestBody int amount);

    @PutMapping("/payments/{payment-id}/cancel")
    ApiResponse<PaymentDTO> cancelPayment(@PathVariable("payment-id") Long paymentId);
}
