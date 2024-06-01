package com.CareemSystem.object.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelPrice {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "model_price_id"
    )
    @SequenceGenerator(
            name = "model_price_id",
            sequenceName = "model_price_id",
            allocationSize = 1
    )
    private Integer id;
    private Integer price;
    private String model;

}
