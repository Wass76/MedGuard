package com.CareemSystem.reservation.Repository;

import com.CareemSystem.reservation.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
//
//@Repository
//public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
//    public List<Reservation> findByBicycleId(Integer bicycleId);
//    public List<Reservation> findByClientId(Integer clientId);
//
//    @Query("SELECT r FROM Reservation r\n" +
//            "WHERE (r.startTime BETWEEN :startTime AND :endTime)\n" +
//            "   OR (r.startTime < :endTime AND r.startTime + INTERVAL '1 hour' * r.duration > :startTime)")
//
////    @Query("SELECT r FROM Reservation r " +
////            "WHERE (r.startTime BETWEEN :startTime AND :endTime) " +
////            "OR (r.startTime < :endTime AND r.startTime + INTERVAL '1 hour' * r.duration > :startTime)")
//    List<Reservation> findOverlappingReservations(LocalDateTime startTime,LocalDateTime endTime);
//}

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    public List<Reservation> findByBicycleId(Integer bicycleId);
    public List<Reservation> findByClientId(Integer clientId);

//    @Query(value = "SELECT r FROM Reservation r " +
//            "WHERE (r.startTime BETWEEN :startTime AND :endTime) " +
//            "OR (r.startTime < :endTime AND r.startTime + INTERVAL '1 hour' * r.duration > :startTime)" , nativeQuery = true)
@Query(value = "SELECT * FROM reservation WHERE (start_time BETWEEN :startTime AND :endTime) " +
        "OR (start_time < :endTime AND start_time + INTERVAL '1 hour' * duration > :startTime)", nativeQuery = true)

List<Reservation> findOverlappingReservations(LocalDateTime startTime, LocalDateTime endTime);
}
