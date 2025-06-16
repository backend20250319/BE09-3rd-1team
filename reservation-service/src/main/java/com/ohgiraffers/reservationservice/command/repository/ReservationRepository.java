package com.ohgiraffers.reservationservice.command.repository;

import com.ohgiraffers.reservationservice.command.entity.Reservation;
import com.ohgiraffers.reservationservice.command.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRoomIdAndStatusAndEndDateGreaterThanEqualAndStartDateLessThanEqual(
            Long roomId,
            ReservationStatus status,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Reservation> findByUserIdOrderByStartDateDesc(Long userId);

    @Query("SELECT r FROM Reservation r WHERE r.roomId = :roomId " +
            "AND r.status = 'CONFIRMED' " +
            "AND r.startDate <= :endDate " +
            "AND r.endDate >= :startDate")
    List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

    Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);
}
