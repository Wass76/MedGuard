package com.MedGuard.task.Entity;

import com.MedGuard.task.Enum.RiskLevel;
import com.MedGuard.task.Enum.TaskState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public class Task {

        @Id
        @GeneratedValue(
                generator = "hub_id",
                strategy = GenerationType.SEQUENCE
        )
        @SequenceGenerator(
                name = "hub_id",
                sequenceName = "hub_id",
                allocationSize = 1
        )
        private Integer id;

        private String patientName;

//        @OneToOne
//        @Column(name = "location", columnDefinition = "geography(Point)")
//        private Location location;
        private Double latitude;
        private Double longitude;

        private String description;
            private TaskState taskState;
            private LocalDate dueDate;
            private LocalTime time;
            private String situationName;
            private RiskLevel riskLevel;

    }
