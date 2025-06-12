package com.ohgiraffers.reservationservice.command.controller;

import com.ohgiraffers.reservationservice.command.dto.ReservationDTO;
import com.ohgiraffers.reservationservice.command.dto.ReservationDateResponse;
import com.ohgiraffers.reservationservice.command.dto.ReservationRequest;
import com.ohgiraffers.reservationservice.command.dto.ReservationResponse;
import com.ohgiraffers.reservationservice.command.service.ReservationService;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 1. 예약(USER)
    // 필요 정보 : 회원 ID(<- token에서 추출), 숙소 ID, 시작 일자, 종료 일자
    //    {
    //            "roomId": 101,
    //            "startDate": "2025-06-15",
    //            "endDate": "2025-06-18"
    //    }
    @PostMapping("/reservations")
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(@RequestBody ReservationRequest reservationRequest) {

        // TODO: token에서 id 받아오기
        Long userId = 1L;

        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(reservationResponse));
    }

    // 2. 예약된 날짜 조회(ALL)
    // 필요 정보 : 년월, 숙소 ID
    //    /reservations/reserved-dates?roomId=101&yearMonth=2025-06
    @GetMapping("/reservations/reserved-dates")
    public ResponseEntity<ApiResponse<ReservationDateResponse>> getReservedDates(
            @RequestParam Long roomId, @RequestParam String yearMonth) {

        ReservationDateResponse reservationDateResponse = reservationService.getReservedDates(roomId, yearMonth);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationDateResponse));
    }


    // 3. 예약 내역 조회(USER)
    // 필요 정보 : 회원 ID(<- token에서 추출)
    @GetMapping("/reservations")
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getReservations() {

        // TODO: token에서 id 받아오기
        Long userId = 1L;

        List<ReservationDTO> reservationList = reservationService.getReservations(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationList));
    }

    // 4. 예약 상세 조회(USER)
    // 필요 정보 : 회원 ID(<- token에서 추출), 예약 ID

    // 5. 예약 취소(USER)
    // 필요 정보 : 회원 ID(<- token에서 추출), 예약 ID
}
