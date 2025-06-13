package com.ohgiraffers.reservationservice.command.client;

import com.ohgiraffers.reservationservice.command.dto.RoomDTO;
import com.ohgiraffers.reservationservice.command.dto.RoomIdListRequest;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import com.ohgiraffers.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 내부에서 user-service를 호출하는 상황
@FeignClient(name = "room-service", configuration = FeignClientConfig.class)
public interface RoomClient {

    // 내부에서 user-service를 호출하는 상황
    @GetMapping("/rooms")
    ApiResponse<List<RoomDTO>> getRoomIdList(@RequestBody RoomIdListRequest request);
}


