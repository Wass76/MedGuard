package com.CareemSystem.review.Response;

import com.CareemSystem.reservation.Model.Reservation;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewResponse {

    private Integer id;
    private Boolean skeleton;
    private Boolean wheels;
    private Boolean breaks;
    private Boolean extension;
    private String notes;
}
