package com.RideShare.favourite;

import com.RideShare.object.Model.Bicycle;
import com.RideShare.user.Model.Client;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
//@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert
public class Favourite {
    @Id
    @SequenceGenerator(
            name = "favourite_id",
            sequenceName = "favourite_id",
            allocationSize = 1

    )
    @GeneratedValue(
            generator = "favourite_id",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private Bicycle bicycle;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private Client client;
}
