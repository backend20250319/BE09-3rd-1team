package com.unobnb.paymentservice.dto;

public class PaymentCancelRequestDto {
    private Long paymentId;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
