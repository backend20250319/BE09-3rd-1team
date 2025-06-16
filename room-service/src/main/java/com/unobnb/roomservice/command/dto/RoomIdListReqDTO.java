package com.unobnb.roomservice.command.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "방 ID 목록 요청 DTO")
public class RoomIdListReqDTO {

    @Schema(description = "조회할 방 ID 리스트", example = "[1, 2, 3]")
    private List<Long> roomIdList;
}
