package com.RideShare.object.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MaintenanceRequest {

    private String type;
    private String description;
    private Integer cost;

    private Integer bicycleId;
    private LocalDate date;

    private String note;

}
