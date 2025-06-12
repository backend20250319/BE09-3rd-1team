package com.ohgiraffers.reservationservice.command.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {

    private final Long reservationId;
}
