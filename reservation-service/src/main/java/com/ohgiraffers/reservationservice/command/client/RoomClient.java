package com.ohgiraffers.reservationservice.command.client;

import com.ohgiraffers.reservationservice.command.dto.RoomDTO;
import com.ohgiraffers.reservationservice.command.dto.RoomIdListReqDTO;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import com.ohgiraffers.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 내부에서 user-service를 호출하는 상황
@FeignClient(name = "room-service", configuration = FeignClientConfig.class)
public interface RoomClient {

    // 내부에서 user-service를 호출하는 상황
    @PostMapping("/api/v1/room-service/list")
    List<RoomDTO> getRoomsByIds(@RequestBody RoomIdListReqDTO request);
}


