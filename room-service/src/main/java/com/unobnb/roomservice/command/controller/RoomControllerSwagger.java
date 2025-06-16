package com.unobnb.roomservice.command.controller;

import com.unobnb.roomservice.command.dto.RoomDTO;
import com.unobnb.roomservice.command.dto.RoomIdListReqDTO;
import com.unobnb.roomservice.command.dto.RoomUpdateReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Room API", description = "숙소(Room) 관련 API")
public interface RoomControllerSwagger {

    @Operation(summary = "방 등록", description = "SELLER만 방을 등록할 수 있습니다.")
    ResponseEntity<RoomDTO> createRoom(String userId, RoomDTO roomDTO);

    @Operation(summary = "전체 방 조회", description = "모든 사용자 접근 가능")
    ResponseEntity<List<RoomDTO>> getAllRooms();

    @Operation(summary = "단일 방 조회", description = "방 ID로 조회")
    ResponseEntity<RoomDTO> getRoomById(Long id);

    @Operation(summary = "방 수정", description = "SELLER만 본인의 방을 수정 가능")
    ResponseEntity<RoomUpdateReqDTO> updateRoom(String userId, Long id, RoomUpdateReqDTO roomUpdateReqDTO);

    @Operation(summary = "방 삭제", description = "SELLER만 본인의 방을 삭제 가능")
    ResponseEntity<Void> deleteRoom(String userId, Long id);

    @Operation(summary = "방 여러 개 조회", description = "SELLER 또는 CUSTOMER가 여러 개의 방 ID를 통해 조회")
    ResponseEntity<List<RoomDTO>> getRoomsByIds(String userId, RoomIdListReqDTO request);
}
