package com.CareemSystem.reservation.Controller;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.reservation.Model.Reservation;
import com.CareemSystem.reservation.Request.ReservationRequest;
import com.CareemSystem.reservation.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/by-reservation-id/{id}")
    public ApiResponseClass getReservationByReservationId(@PathVariable Integer id) {
        return reservationService.getReservation(id);
    }

    @GetMapping("/by-bicycle-id/{id}")
    public ApiResponseClass getAllReservationsByBicycleId(@PathVariable Integer id) {
        return reservationService.getAllReservationsByBicycleId(id);
    }


    @GetMapping("/by-client-id/{id}")
    public ApiResponseClass getAllReservationsByClientId(@PathVariable Integer id) {
        return reservationService.getAllReservationsByClientId(id);
    }

    @PostMapping
    public ApiResponseClass createReservation(@RequestBody ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @PutMapping("/update-to-during-Reservation/{id}")
    public ApiResponseClass updateReservationToDuringReservation(Integer id){
        return reservationService.updateReservationToDuringReservation(id);
    }


    @PutMapping("/update-to-finished/{id}")
    public ApiResponseClass updateReservationToFinished(@PathVariable Integer id){
        return reservationService.updateReservationToFinished(id);
    }
    @DeleteMapping("{id}")
    public ApiResponseClass deleteReservation(@PathVariable Integer id) {
        return reservationService.deleteReservation(id);
    }



}
