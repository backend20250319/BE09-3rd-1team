package com.ohgiraffers.reservationservice.command.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ReservationDateResponse {
    private Long roomId;
    private String yearMonth; // 예: "2025-06"
    private List<LocalDate> reservedDates;
}
