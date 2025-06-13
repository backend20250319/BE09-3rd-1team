package com.unobnb.roomservice.controller;

import com.unobnb.roomservice.dto.RoomDTO;
import com.unobnb.roomservice.dto.RoomIdListReqDTO;
import com.unobnb.roomservice.dto.RoomUpdateReqDTO;
import com.unobnb.roomservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/room-service")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO saveRoom = roomService.save(roomDTO);
        return ResponseEntity.ok(saveRoom);
    }


    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomUpdateReqDTO> updateRoom(
            @PathVariable Long id,
            @RequestBody RoomUpdateReqDTO roomUpdateReqDTO
    ) {
        roomUpdateReqDTO.setId(id);
        return ResponseEntity.ok(roomService.updated(roomUpdateReqDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public ResponseEntity<  List<RoomDTO>> getRoomsByIds(@RequestBody RoomIdListReqDTO request) {
        List<RoomDTO> rooms = roomService.findRoomsByIds(request.getRoomIdList());
        return ResponseEntity.ok(rooms);
    }
}
