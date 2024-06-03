package com.CareemSystem.object.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "maintenance_id"
    )
    @SequenceGenerator(
            sequenceName = "maintenance_id",
            name = "maintenance_id",
            allocationSize = 1
    )
    private Integer id;
    private String type;
    private String description;
    private Integer cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bicycle_id")
    private Bicycle bicycle;
    private LocalDate date;

    private String note;
}
