package com.ohgiraffers.reservationservice.command.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentDTO {
    private Long paymentId;             // DB PK
    private String paymentNo;           // PG 결제 번호
    private int price;                  // 결제 금액
    private LocalDateTime approvedAt;   // 승인 일시
    private LocalDateTime cancelledAt;  // 취소 일시
    private String status;
}

