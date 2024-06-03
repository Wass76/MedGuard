package com.CareemSystem.review.Model;

import com.CareemSystem.reservation.Model.Reservation;
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
public class Review {

    @Id
    @SequenceGenerator(
            name = "review_id",
            sequenceName = "review_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_id"
    )
    private Integer id;

    private Boolean skeleton;
    private Boolean wheels;
    private Boolean breaks;
    private Boolean extension;
    private String notes;
    @OneToOne
    private Reservation reservation;
    //TODO : add admin id

}
