package com.MedGuard.task.Request;

import com.MedGuard.task.Enum.RiskLevel;
import com.MedGuard.task.Enum.TaskState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TaskRequest {

    private String situationName;
    private Double latitude;
    private Double longitude;
    private String description;
    private TaskState taskState;
    private LocalDate dueDate;
    private LocalTime time;
    private String patientName;
    private RiskLevel riskLevel;
}
