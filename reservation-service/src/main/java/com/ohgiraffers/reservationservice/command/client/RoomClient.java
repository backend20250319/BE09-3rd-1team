package com.ohgiraffers.reservationservice.command.client;

import com.ohgiraffers.reservationservice.command.dto.RoomDTO;
import com.ohgiraffers.reservationservice.command.dto.RoomListRequest;
import com.ohgiraffers.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 내부에서 room-service를 호출하는 상황
@FeignClient(name = "room-service", url = "http://localhost:8000/api/v1/room-service", configuration = FeignClientConfig.class)
public interface RoomClient {
    // 내부에서 room-service를 호출하는 상황
    @GetMapping("/rooms/{id}")
    RoomDTO getRoomById(@PathVariable("id") Long id);

    // 내부에서 user-service를 호출하는 상황
    @PostMapping("/rooms/batch")
    List<RoomDTO> getRoomsByIds(@RequestBody RoomListRequest request);
}


