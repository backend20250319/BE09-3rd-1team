package com.ohgiraffers.reservationservice.command.service;

import com.ohgiraffers.reservationservice.command.client.PaymentClient;
import com.ohgiraffers.reservationservice.command.dto.ReservationDTO;
import com.ohgiraffers.reservationservice.command.dto.ReservationDateResponse;
import com.ohgiraffers.reservationservice.command.dto.ReservationRequest;
import com.ohgiraffers.reservationservice.command.dto.ReservationResponse;
import com.ohgiraffers.reservationservice.command.entity.Reservation;
import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import com.ohgiraffers.reservationservice.command.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentClient paymentClient;

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

        // 1. 결제 요청
//        Long paymentId = paymentClient.getPaymentId(null);
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

        return null;
    }
}
