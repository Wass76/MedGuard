package com.CareemSystem.reservation.Request;

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
public class ReservationRequest {

    private Integer clientId;
    private Integer bicycleId;
    private Integer fromHubId;
    private Integer toHubId;
    private Double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reservationStatus;



}
