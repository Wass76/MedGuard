package com.CareemSystem.reservation.Model;

import com.CareemSystem.hub.Hub;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.reservation.Enum.ReservationStatus;
import com.CareemSystem.user.Model.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;
    @ManyToOne(cascade = CascadeType.ALL)
    private Bicycle bicycle;
    @ManyToOne(cascade = CascadeType.ALL)
    private Hub from;
    @ManyToOne(cascade = CascadeType.ALL)
    private Hub to;
    private Double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated
    private ReservationStatus reservationStatus;



}
