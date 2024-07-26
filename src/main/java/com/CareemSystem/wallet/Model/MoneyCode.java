package com.CareemSystem.wallet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyCode {
    @Id
    @SequenceGenerator(
            sequenceName = "money_code_id",
            name = "money_code_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "money_code_id"
    )
    private Integer id;

    private String code;

    private Double balance;

    private boolean valid;


}
