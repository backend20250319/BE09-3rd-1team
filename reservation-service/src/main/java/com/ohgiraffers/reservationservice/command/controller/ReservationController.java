package com.ohgiraffers.reservationservice.command.controller;

import com.ohgiraffers.reservationservice.command.dto.*;
import com.ohgiraffers.reservationservice.command.service.ReservationService;
import com.ohgiraffers.reservationservice.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(name = "Reservation API", description = "예약 관련 기능을 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // 1. 예약(권한 = CUSTOMER)
    @Operation(
            summary = "숙소 예약", description = "숙소를 예약합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @AuthenticationPrincipal String userId,
            @Valid @RequestBody ReservationRequest reservationRequest) {

        ReservationResponse reservationResponse = reservationService.createReservation(
                reservationRequest, Long.valueOf(userId));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(reservationResponse));
    }

    // 2. 예약된 날짜 조회(권한 = NONE)
    @Operation(
            summary = "예약된 날짜 조회",
            description = """
                        숙소에 이미 예약된 날짜 리스트를 조회합니다.
                        응답 데이터를 통해 예약 가능 여부를 판별할 수 있습니다.
            """)
    @GetMapping("/{roomId}/reserved-dates")
    public ResponseEntity<ApiResponse<ReservationDateResponse>> getReservedDates(
            @Parameter(description = "조회할 숙소 ID", example = "1")
            @PathVariable Long roomId,
            @Parameter(description = "조회할 연월 (yyyy-MM)", example = "2025-06")
            @Pattern(regexp = "\\d{4}-\\d{2}", message = "yearMonth must be in the format yyyy-MM")
            @RequestParam String yearMonth) {

        ReservationDateResponse reservationDateResponse = reservationService.getReservedDates(roomId, yearMonth);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationDateResponse));
    }


    // 3. 예약 목록 조회(권한 = CUSTOMER)
    // 필요 정보 : 회원 ID(<- token에서 추출)
    @Operation(summary = "예약 목록 조회", description = "회원의 예약 목록을 조회합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getReservations(@AuthenticationPrincipal String userId) {

        List<ReservationDTO> reservationList = reservationService.getReservations(Long.valueOf(userId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationList));
    }

    // 5. 예약 취소(권한 = CUSTOMER)
    @Operation(summary = "예약 취소", description = "예약을 취소합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<ApiResponse<ReservationCancelResponse>> cancelReservation(
            @AuthenticationPrincipal String userId,
            @Parameter(description = "취소할 예약 ID", example = "1")
            @PathVariable Long reservationId) {

        ReservationCancelResponse reservationCancelResponse = reservationService.cancelReservation(Long.valueOf(userId), reservationId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reservationCancelResponse));
    }
}
