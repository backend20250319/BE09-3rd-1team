package com.unobnb.paymentservice.dto;

import java.time.LocalDateTime;

public class PaymentResponseDto {
    private Long paymentId;             // DB PK
    private String paymentNo;           // PG 결제 번호
    private int price;                  // 결제 금액
    private LocalDateTime approvedAt;   // 승인 일시
    private LocalDateTime cancelledAt;  // 취소 일시
    private String status;              // 상태 ("SUCCESS", "FAILED", "CANCELLED")

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
