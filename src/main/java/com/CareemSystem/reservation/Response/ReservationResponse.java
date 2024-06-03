package com.CareemSystem.reservation.Response;

import com.CareemSystem.hub.Hub;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.reservation.Enum.ReservationStatus;
import com.CareemSystem.user.Model.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ReservationResponse {
    private Integer id;
    private Client client;
    private Bicycle bicycle;
    private Hub from;
    private Hub to;
    private Double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reservationStatus;
}
