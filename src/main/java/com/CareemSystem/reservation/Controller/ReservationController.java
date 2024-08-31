package com.CareemSystem.reservation.Controller;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.reservation.Model.Reservation;
import com.CareemSystem.reservation.Request.ReservationRequest;
import com.CareemSystem.reservation.Service.ReservationService;
import com.CareemSystem.wallet.Request.PaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/v1/reservation")
@Tag(
        name = "Bicycle reservation"
)
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/by-reservation-id/{id}")
    @Operation(
            description = "This endpoint build to Get reservation by id from our system",
            summary = "Get reservation by id",
            responses = {
                    @ApiResponse(
                            description = "Get reservation done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass getReservationByReservationId(@PathVariable Integer id) {
        return reservationService.getReservation(id);
    }

    @GetMapping("/by-bicycle-id/{id}")
    @Operation(
            description = "This endpoint build to Get reservations by bicycle id in our system",
            summary = "Get reservations by bicycle id",
            responses = {
                    @ApiResponse(
                            description = "Get done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass getAllReservationsByBicycleId(@PathVariable Integer id) {
        return reservationService.getAllReservationsByBicycleId(id);
    }


    @GetMapping("/by-client-id/{id}")
    @Operation(
            description = "This endpoint build to Get reservations by client id in our system",
            summary = "Get reservations by client id",
            responses = {
                    @ApiResponse(
                            description = "Get reservations Done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass getAllReservationsByClientId(@PathVariable Integer id) {
        return reservationService.getAllReservationsByClientId(id);
    }

    @PostMapping()
    @Operation(
            description = "This endpoint build to Create new reservation to our system",
            summary = "Create new reservation",
            responses = {
                    @ApiResponse(
                            description = "Create done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass createReservation(@RequestBody ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @PutMapping("/update-to-during-Reservation/{id}")
    @Operation(
            description = "This endpoint build to update reservation status to 'duringReservation' by id",
            summary = "Make status of reservation 'during Reservation' ",
            responses = {
                    @ApiResponse(
                            description = "Edit done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass updateReservationToDuringReservation(Integer id){
        return reservationService.updateReservationToDuringReservation(id);
    }


    @PutMapping("/update-to-finished/{id}")
    @Operation(
            description = "This endpoint build to update reservation status to 'Finished' by id",
            summary = "Make status of reservation 'Finished' ",
            responses = {
                    @ApiResponse(
                            description = "Edit done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ApiResponseClass updateReservationToFinished(@PathVariable Integer id){
        return reservationService.updateReservationToFinished(id);
    }
// TODO: make this endpoint
//    @GetMapping("My-history")
//    public ApiResponseClass getReservationByState(){
//
//    }

    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to Delete reservation by id from our system",
            summary = "Delete reservation by id",
            responses = {
                    @ApiResponse(
                            description = "Delete done successfully",
                            responseCode = "200"
                    )
            }
//            ,
//            hidden = true
    )
    public ApiResponseClass deleteReservation(@PathVariable Integer id) {
        return reservationService.deleteReservation(id);
    }

    @PostMapping("reseravation-payment")
    public ApiResponseClass payment(@RequestBody PaymentRequest request) {
        return reservationService.payForReservation(request);

    }



}
