package com.CareemSystem.reservation.Repository;

import com.CareemSystem.reservation.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    public List<Reservation> findByBicycleId(Integer bicycleId);
    public List<Reservation> findByClientId(Integer clientId);

    @Query("SELECT r FROM Reservation r WHERE r.startTime < :endTime AND r.endTime > :startTime")
    List<Reservation> findOverlappingReservations(LocalDateTime startTime,LocalDateTime endTime);
}
