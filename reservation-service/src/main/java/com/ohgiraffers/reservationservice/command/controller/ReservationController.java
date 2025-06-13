package com.ohgiraffers.reservationservice.command.controller;

import com.ohgiraffers.reservationservice.command.dto.*;
import com.ohgiraffers.reservationservice.command.service.ReservationService;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservation API", description = "예약 관련 기능을 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // 1. 예약(USER)
    // 필요 정보 : 회원 ID(<- token에서 추출), 숙소 ID, 시작 일자, 종료 일자
    //    {
    //            "roomId": 101,
    //            "startDate": "2025-06-15",
    //            "endDate": "2025-06-18"
    //    }
    @Operation(summary = "예약 생성", description = "숙소 예약을 생성합니다. (토큰에서 사용자 ID 추출 필요)")
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @AuthenticationPrincipal String userId, @RequestBody ReservationRequest reservationRequest) {

        ReservationResponse reservationResponse = reservationService.createReservation(
                reservationRequest, Long.valueOf(userId));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(reservationResponse));
    }

    // 2. 예약된 날짜 조회(ALL)
    // 필요 정보 : 년월, 숙소 ID
    //    /reservations/reserved-dates?roomId=101&yearMonth=2025-06
    @GetMapping("/reserved-dates")
    public ResponseEntity<ApiResponse<ReservationDateResponse>> getReservedDates(
            @RequestParam Long roomId, @RequestParam String yearMonth) {

        ReservationDateResponse reservationDateResponse = reservationService.getReservedDates(roomId, yearMonth);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationDateResponse));
    }


    // 3. 예약 내역 조회(USER)
    // 필요 정보 : 회원 ID(<- token에서 추출)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getReservations(@AuthenticationPrincipal String userId) {

        List<ReservationDTO> reservationList = reservationService.getReservations(Long.valueOf(userId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationList));
    }

    // 5. 예약 취소(USER)
    // 필요 정보 : 예약 ID
    @PutMapping("/{reservation-id}/cancel")
    public ResponseEntity<ApiResponse<PaymentDTO>> cancelReservation(
            @AuthenticationPrincipal String userId,
            @PathVariable("reservation-id") Long reservationId) {

        PaymentDTO paymentDTO = reservationService.cancelReservation(reservationId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(paymentDTO));
    }
}
