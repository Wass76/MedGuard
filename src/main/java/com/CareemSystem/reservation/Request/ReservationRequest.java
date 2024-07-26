package com.CareemSystem.reservation.Request;

import com.CareemSystem.wallet.Enum.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReservationRequest {

//    private Integer clientId;
    private Integer bicycleId;
    private Integer fromHubId;
    private Integer toHubId;
    private Double duration;

//    @FutureOrPresent(message = "Couldn't do it")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime startTime;

//    @FutureOrPresent(message = "Couldn't do it")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime endTime;

    private String reservationStatus;
    private PaymentMethod paymentMethod;



}
