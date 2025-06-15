package com.unobnb.paymentservice.dto;

import java.time.LocalDateTime;

public class PaymentResponseDto {
    private Long paymentId;
    private LocalDateTime paymentDate;
    private int status; // 0: 실패, 1: 성공, 2: 취소

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
