package com.ohgiraffers.reservationservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomListRequest {

    @Schema(description = "조회할 숙소 ID 리스트", example = "[101, 104, 105, 107]")
    private List<Long> roomIdList;
}

