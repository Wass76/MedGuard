package com.CareemSystem.favourite;

import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.user.Model.Client;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Bicycle bicycle;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Client client;
}
