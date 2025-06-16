package com.ohgiraffers.reservationservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCancelResponse {

    @Schema(description = "취소된 예약 ID", example = "10001")
    private final Long reservationId;
}
