package com.ohgiraffers.reservationservice.command.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {

    private final Long roomId;
    private final LocalDate startDate;
    private final LocalDate endDate;
}
