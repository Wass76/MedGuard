package com.CareemSystem.review.Request;

import com.CareemSystem.reservation.Model.Reservation;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class ReviewRequest {

    private Boolean skeleton;
    private Boolean wheels;
    private Boolean breaks;
    private Boolean extension;
    private String notes;
    private Integer reservationId;
}
