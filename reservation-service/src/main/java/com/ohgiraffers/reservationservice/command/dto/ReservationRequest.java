package com.ohgiraffers.reservationservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {

    @Schema(description = "숙소 ID", example = "7")
    private final Long roomId;

    @Schema(description = "예약 시작일", example = "2025-06-11")
    private final LocalDate startDate;

    @Schema(description = "예약 종료일", example = "2025-06-12")
    private final LocalDate endDate;

    @Schema(description = "결제 금액", example = "300000")
    private final int amount;
}
