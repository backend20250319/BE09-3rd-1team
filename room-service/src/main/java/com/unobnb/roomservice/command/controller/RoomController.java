package com.unobnb.roomservice.command.controller;

import com.unobnb.roomservice.command.dto.RoomDTO;
import com.unobnb.roomservice.command.dto.RoomIdListReqDTO;
import com.unobnb.roomservice.command.dto.RoomUpdateReqDTO;
import com.unobnb.roomservice.command.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@AuthenticationPrincipal String userId, @RequestBody RoomDTO roomDTO) {
        roomDTO.setSellerId(Long.valueOf(userId));
        return ResponseEntity.ok(roomService.save(roomDTO));
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
            @AuthenticationPrincipal String userId,
            @PathVariable Long id,
            @RequestBody RoomUpdateReqDTO roomUpdateReqDTO) {
        roomUpdateReqDTO.setId(id);
        roomUpdateReqDTO.setSellerId(Long.valueOf(userId));
        return ResponseEntity.ok(roomService.updated(roomUpdateReqDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id) {
        roomService.deleteRoomWithAuth(id, Long.valueOf(userId));
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SELLER') or hasRole('CUSTOMER')")
    @PostMapping("/batch")
    public ResponseEntity<List<RoomDTO>> getRoomsByIds(
            @AuthenticationPrincipal String userId,
            @RequestBody RoomIdListReqDTO request) {
        return ResponseEntity.ok(roomService.findRoomsByIds(request.getRoomIdList()));
    }

    private Long getSellerIdFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Long) auth.getPrincipal();
    }
}
