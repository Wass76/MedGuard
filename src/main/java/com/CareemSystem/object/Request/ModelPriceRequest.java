package com.CareemSystem.object.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelPriceRequest {

    @NotBlank(message = "can't be null")
    private String modelName;
    @NotNull(message = "can't be null")
    private Double price;
}
