package com.ohgiraffers.reservationservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {
    @Schema(description = "생성된 예약 ID", example = "10001")
    private final Long reservationId;
}
