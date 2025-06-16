package com.ohgiraffers.reservationservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {

    @Schema(description = "예약할 숙소 ID", example = "7")
    @NotNull(message = "roomId is required")
    private final Long roomId;

    @Schema(description = "예약 시작일", example = "2025-06-11")
    @NotNull(message = "startDate is required")
    private final LocalDate startDate;

    @Schema(description = "예약 종료일", example = "2025-06-12")
    @NotNull(message = "endDate is required")
    private final LocalDate endDate;

    @Schema(description = "결제 금액", example = "300000")
    @NotNull(message = "amount is required")
    private final int amount;

    // 유효성 검증 : 시작일 >= 오늘
    @AssertTrue(message = "startDate must be today or a future date")
    public boolean isStartDateValid() {
        return startDate == null || !startDate.isBefore(LocalDate.now());
    }

    // 유효성 검증 :
    @AssertTrue(message = "endDate must be the same as or after startDate")
    public boolean isDateOrderValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}
