package com.CareemSystem.wallet.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoneyCodeResponse {

    private Integer id;
    private String code;
    private Double amount;

}
