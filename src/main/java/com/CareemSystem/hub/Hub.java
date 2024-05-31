package com.CareemSystem.hub;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Hub {

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

    private String name;
    private Float latitude;
    private Float longitude;
    private String description;

}
