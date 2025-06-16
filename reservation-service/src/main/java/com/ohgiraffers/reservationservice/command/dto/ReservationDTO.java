package com.ohgiraffers.reservationservice.command.dto;

import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationDTO {
    @Schema(description = "예약 ID", example = "111")
    private Long reservationId;

    @Schema(description = "회원 ID", example = "7")
    private Long userId;

    @Schema(description = "숙소 ID", example = "22")
    private Long roomId;

    @Schema(description = "결제 ID", example = "777")
    private Long paymentId;

    @Schema(description = "예약번호", example = "20250601202506011109")
    private String resvNo;

    @Schema(description = "예약 시작일", example = "2025-06-11")
    private LocalDate startDate;

    @Schema(description = "예약 종료일", example = "2025-06-12")
    private LocalDate endDate;

    @Schema(description = "예약 상태", example = "CONFIRMED/CANCELLED")
    private String status;

    private RoomDTO room;
}
