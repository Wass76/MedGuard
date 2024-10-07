package com.RideShare.reservation.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationByStatusRequest {

    @JsonProperty
    private String reservationStatus;
}
