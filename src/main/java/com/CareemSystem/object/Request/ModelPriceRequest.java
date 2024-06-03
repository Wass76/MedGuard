package com.CareemSystem.object.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelPriceRequest {

    private String modelName;
    private Integer price;
}
