package com.CareemSystem.reservation.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReservationResponse {
    private Integer id;
    private String client;
    private String bicycle;
    private String from;
    private String to;
    private Double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reservationStatus;
    private Double price;
}
