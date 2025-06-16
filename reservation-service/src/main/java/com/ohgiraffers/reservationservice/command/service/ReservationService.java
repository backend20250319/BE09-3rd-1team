package com.ohgiraffers.reservationservice.command.service;

import com.ohgiraffers.reservationservice.command.client.PaymentClient;
import com.ohgiraffers.reservationservice.command.client.RoomClient;
import com.ohgiraffers.reservationservice.command.dto.*;
import com.ohgiraffers.reservationservice.command.entity.Reservation;
import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import com.ohgiraffers.reservationservice.command.repository.ReservationRepository;
import feign.FeignException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentClient paymentClient;
    private final RoomClient roomClient;

    // 예약 생성
    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest, Long userId) {

        // 1. 숙소 ID 검증
        try {
            RoomDTO room = roomClient.getRoomById(reservationRequest.getRoomId());
            if (!room.getId()
                    .equals(reservationRequest.getRoomId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }

        // 2. 중복 예약 방지
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                reservationRequest.getRoomId(),
                reservationRequest.getStartDate(),
                reservationRequest.getEndDate()
        );
        if (!overlappingReservations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Overlapping Reservations");
        }


        // 결제 생성
        Long paymentId;
        try {
            PaymentDTO paymentDTO = paymentClient.processPayment(new PaymentRequest(reservationRequest.getAmount()));
            paymentId = paymentDTO.getPaymentId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Payment failed");
        }

        // 2. 예약 번호 생성
        String resvNo = generateReservationNo(
                reservationRequest.getStartDate()
                        .toString(),
                reservationRequest.getEndDate()
                        .toString()
        );

        // 3. 예약 저장
        Reservation reservation = Reservation.builder()
                .userId(userId)
                .roomId(reservationRequest.getRoomId())
                .paymentId(paymentId)
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .resvNo(resvNo)
                .status(ReservationStatus.CONFIRMED)
                .build();
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.builder()
                .reservationId(savedReservation.getId())
                .build();
    }

    // 예약 날짜 조회
    public ReservationDateResponse getReservedDates(Long roomId, String yearMonth) {
        YearMonth ym = YearMonth.parse(yearMonth);      // "2025-06"
        LocalDate startDate = ym.atDay(1);   // 2025-06-01
        LocalDate endDate = ym.atEndOfMonth();          // 2025-06-30

        // 1. 숙소 ID 검증
        RoomDTO room;
        try {
            room = roomClient.getRoomById(roomId);
            if (!room.getId()
                    .equals(roomId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found");
        }

        // CONFIRMED 확정 상태인 예약만 조회
        List<Reservation> reservations = reservationRepository
                .findByRoomIdAndStatusAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
                        room.getId(),
                        ReservationStatus.CONFIRMED,
                        startDate,
                        endDate
                );

        // 예약 날짜 리스트 생성
        List<LocalDate> reservedDates = new ArrayList<>();
        for (Reservation reservation : reservations) {
            LocalDate current = reservation.getStartDate();
            while (!current.isAfter(reservation.getEndDate())) {
                if (!current.isBefore(startDate) && !current.isAfter(endDate)) {
                    reservedDates.add(current);
                }
                current = current.plusDays(1);
            }
        }

        // 날짜 정렬
        reservedDates.sort(Comparator.naturalOrder());

        return ReservationDateResponse.builder()
                .roomId(roomId)
                .yearMonth(yearMonth)
                .reservedDates(reservedDates)
                .build();
    }

    // 예약 리스트 조회
    public List<ReservationDTO> getReservations(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserIdOrderByStartDateDesc(userId);

        // 1. 예약 목록에서 roomId들 추출
        List<Long> roomIds = reservations.stream()
                .map(Reservation::getRoomId)
                .distinct()
                .collect(Collectors.toList());

        // 2. FeignClient로 room 정보 조회
        RoomListRequest request = RoomListRequest.builder()
                .roomIdList(roomIds)
                .build();

        // 3. 예약 숙소 목록 요청
        List<RoomDTO> rooms = roomClient.getRoomsByIds(request);

        // 3. roomId -> RoomDTO 맵핑
        Map<Long, RoomDTO> roomMap = rooms.stream()
                .collect(Collectors.toMap(RoomDTO::getId, Function.identity()));

        // 4. Reservation → ReservationDTO로 변환
        return reservations.stream()
                .map(reservation -> ReservationDTO.builder()
                        .reservationId(reservation.getId())
                        .userId(userId)
                        .roomId(reservation.getRoomId())
                        .paymentId(reservation.getPaymentId())
                        .resvNo(reservation.getResvNo())
                        .startDate(reservation.getStartDate())
                        .endDate(reservation.getEndDate())
                        .status(reservation.getStatus()
                                .toString())
                        .room(roomMap.get(reservation.getRoomId()))
                        .build())
                .collect(Collectors.toList());
    }

    // 예약 취소
    public ReservationCancelResponse cancelReservation(Long userId, Long reservationId) {
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "do not have permission"));

        // 이미 취소된 예약
        if(reservation.getStatus().equals(ReservationStatus.CANCELLED)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "This reservation has already been cancelled"
            );
        }

        // 24시간 이전 취소만 허용
        LocalDateTime now = LocalDateTime.now();

        // startDate는 자정(00:00) 기준이므로 LocalDateTime으로 변환
        LocalDateTime reservationStartDateTime = reservation.getStartDate()
                .atStartOfDay();

        // 예약 시간이 현재로부터 24시간 이내면 취소 불가
        if (reservationStartDateTime.isBefore(now.plusHours(24))) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Reservations can only be cancelled 24 hours prior to the check-in time"
            );
        }

        Long paymentId;
        try {
            PaymentDTO paymentDTO = paymentClient.cancelPayment(new PaymentCancelRequest(reservation.getPaymentId()));
            if (!paymentDTO.getStatus()
                    .equals("CANCELLED")) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Payment cancellation failed");
            }
            paymentId = paymentDTO.getPaymentId();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Payment cancellation failed");
        }

        // 예약 취소
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);


        return ReservationCancelResponse.builder()
                .reservationId(reservation.getId())
                .build();
    }

    // 예약 번호 생성
    public String generateReservationNo(String startDate, String endDate) {
        // 날짜 형식을 yyyyMMdd로 포맷
        String start = startDate.replaceAll("-", ""); // ex: 2025-06-12 → 20250612
        String end = endDate.replaceAll("-", "");

        // 랜덤 숫자 4자리 생성
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // 1000~9999

        return start + end + randomNumber;
    }
}
