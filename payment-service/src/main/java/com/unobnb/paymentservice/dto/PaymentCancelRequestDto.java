package com.unobnb.paymentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 취소 요청 DTo")
public class PaymentCancelRequestDto {

    @Schema(description = "결제 번호", example = "1")
    private Long paymentId;
    
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
