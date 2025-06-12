package com.ohgiraffers.reservationservice.command.repository;

import com.ohgiraffers.reservationservice.command.entity.Reservation;
import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRoomIdAndStatusAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
            Long roomId,
            ReservationStatus status,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Reservation> findByUserIdOrderByStartDateDesc(Long userId);
}
