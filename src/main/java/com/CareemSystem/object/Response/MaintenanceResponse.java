package com.CareemSystem.object.Response;

import com.CareemSystem.object.Model.Bicycle;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MaintenanceResponse {

    private Integer id;
    private String type;
    private String description;
    private Integer cost;

    private Integer bicycleId;
    private LocalDate date;

    private String note;
}
