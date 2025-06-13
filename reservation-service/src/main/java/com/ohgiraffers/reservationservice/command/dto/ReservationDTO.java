package com.ohgiraffers.reservationservice.command.dto;

import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationDTO {
    private Long reservationId;
    private Long userId;
    private Long roomId;
    private Long paymentId;
    private String resvNo;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private RoomDTO room;
}
