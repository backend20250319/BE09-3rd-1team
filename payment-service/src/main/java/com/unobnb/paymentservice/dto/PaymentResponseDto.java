package com.unobnb.paymentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "결제 응답 DTO")
public class PaymentResponseDto {

    @Schema(description = "결제 Id", example = "1")
    private Long paymentId;             // DB PK
    @Schema(description = "PG 결제 번호", example = "DB PK01")
    private String paymentNo;           // PG 결제 번호
    @Schema(description = "금액", example = "50000")
    private int price;                  // 결제 금액

    @Schema(description = "요청 시간", example = "2020.01.01 01:01:01")
    private LocalDateTime approvedAt;   // 승인 일시

    @Schema(description = "취소 시간", example = "2020.01.01 01:01:01")
    private LocalDateTime cancelledAt;  // 취소 일시

    @Schema(description = "상태", example = "SUCCESS")
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
