package com.ohgiraffers.reservationservice.command.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class RoomIdListRequest {
    private List<Long> roomIdList;
}

