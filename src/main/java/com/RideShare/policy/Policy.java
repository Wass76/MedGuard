package com.RideShare.policy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    @Id
    @SequenceGenerator(
            name = "policy_id",
            sequenceName = "policy_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "policy_id"
    )
    private Integer id;
    private String title;
    @Column(columnDefinition = "Text")
    private String  description;
}
