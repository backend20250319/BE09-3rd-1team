package com.ohgiraffers.reservationservice.command.dto;

import lombok.*;

@Getter
@Builder
public class RoomDTO {
    private Long roomId;
    private String accommodationName;
    private String location;
    private String roomType;
    private int pricePerDay;
    private Long sellerId;
}