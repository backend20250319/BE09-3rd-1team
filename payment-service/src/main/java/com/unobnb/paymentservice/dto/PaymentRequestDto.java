package com.unobnb.paymentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "결제 요청 DTO")
public class PaymentRequestDto {

    @Schema(description = "가격", example = "20000")
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
