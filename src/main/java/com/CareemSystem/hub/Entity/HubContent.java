package com.CareemSystem.hub.Entity;

import com.CareemSystem.object.Model.Bicycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HubContent {
    @Id
    @SequenceGenerator(
            name = "hubContent_id",
            sequenceName = "hubContent_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hubContent_id"
    )
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private Hub hub;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Bicycle> bicycles;

    private String note;

}
