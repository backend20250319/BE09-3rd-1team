package com.ohgiraffers.reservationservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelRequest {
    private Long paymentId;
}
