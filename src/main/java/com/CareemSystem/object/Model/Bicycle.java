package com.CareemSystem.object.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bicycle {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "object_id"
    )
    @SequenceGenerator(
            name = "object_id",
            sequenceName = "object_id",
            allocationSize = 1
    )
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private ModelPrice model_price;
    private Integer size;
    private String type;
    private String note;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Maintenance> maintenance;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Extension> extension;

}
