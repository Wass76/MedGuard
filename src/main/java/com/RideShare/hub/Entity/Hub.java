package com.RideShare.hub.Entity;

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

//        @OneToOne
//        @Column(name = "location", columnDefinition = "geography(Point)")
//        private Location location;
        private Double latitude;
        private Double longitude;

        private String description;

    }
