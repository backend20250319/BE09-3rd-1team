package com.ohgiraffers.reservationservice.command.service;

import com.ohgiraffers.reservationservice.command.client.PaymentClient;
import com.ohgiraffers.reservationservice.command.client.RoomClient;
import com.ohgiraffers.reservationservice.command.dto.*;
import com.ohgiraffers.reservationservice.command.entity.Reservation;
import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import com.ohgiraffers.reservationservice.command.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String generateReservationNo(String startDate, String endDate) {
        // 날짜 형식을 yyyyMMdd로 포맷
        String start = startDate.replaceAll("-", ""); // ex: 2025-06-12 → 20250612
        String end = endDate.replaceAll("-", "");

        // 랜덤 숫자 4자리 생성
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // 1000~9999

        return start + end + randomNumber;
    }

    // 예약 생성
    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest, Long userId) {

        // TODO 실제 요청 필요
//        PaymentDTO paymentDTO = paymentClient.createPayment(reservationRequest.getAmount()).getData();
//        Long paymentId = paymentDTO.getPaymentId();
        Long paymentId = 1L;

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

        // CONFIRMED 확정 상태인 예약만 조회
        List<Reservation> reservations = reservationRepository
                .findByRoomIdAndStatusAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
                        roomId,
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
        RoomIdListRequest request = RoomIdListRequest.builder()
                .roomIdList(roomIds)
                .build();

        // TODO : 실제 요청 필요
//        List<RoomDTO> rooms = roomClient.getRoomIdList(request)
//                .getData();

        // TODO : 임시 -> 삭제
        List<RoomDTO> rooms = roomIds.stream()
                .map(roomId -> RoomDTO.builder()
                        .roomId(roomId)
                        .accommodationName("숙소명_" + roomId) // 테스트용 이름
                        .location("테스트 지역")
                        .roomType("스탠다드")
                        .pricePerDay(100000)
                        .sellerId(roomId + 100)
                        .build())
                .toList();

        // 3. roomId -> RoomDTO 맵핑
        Map<Long, RoomDTO> roomMap = rooms.stream()
                .collect(Collectors.toMap(RoomDTO::getRoomId, Function.identity()));

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
                        .status(reservation.getStatus().toString())
                        .room(roomMap.get(reservation.getRoomId()))
                        .build())
                .collect(Collectors.toList());
    }

    // 예약 취소
    public PaymentDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));

//        // TODO 실제 요청 필요
//        PaymentDTO paymentDTO = paymentClient.cancelPayment(reservation.getPaymentId()).getData();

        // TODO : 임시-> 삭제
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .paymentId(reservation.getId())
                .paymentNo("PAY20250612001")
                .amount(150000)
                .approvedAt(LocalDateTime.of(2025, 6, 10, 14, 30))
                .cancelledAt(LocalDateTime.of(2025, 6, 12, 9, 15))
                .status("CANCELLED")
                .build();


        return paymentDTO;
    }
}
