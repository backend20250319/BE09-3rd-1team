package com.ohgiraffers.reservationservice.command.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ReservationDateResponse {

    @Schema(description = "숙소 ID", example = "11")
    private Long roomId;
    @Schema(description = "조회할 연도/월", example = "2025-06")
    private String yearMonth;
    @Schema(description = "예약된 날짜 리스트", example = "[\"2025-06-11\", \"2025-06-12\"]")
    private List<LocalDate> reservedDates;
}
