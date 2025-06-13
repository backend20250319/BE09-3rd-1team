package com.ohgiraffers.reservationservice.command.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentDTO {

    private Long paymentId;                  // 결제 식별자 (DB PK)
    private String paymentNo;                // 결제 번호 (예: PG사 고유번호 등)
    private int amount;                      // 결제 금액
    private LocalDateTime approvedAt;        // 결제 승인 일시
    private LocalDateTime cancelledAt;       // 결제 취소 일시
    private String status;                   // 결제 상태 (예: "APPROVED", "CANCELLED")
}

