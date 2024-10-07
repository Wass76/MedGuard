package com.RideShare.policy;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicyRequest {
    @NotBlank(message = "title can't be blank")
    private String title;
    @NotBlank(message = "description can't be blank")
    private String description;
}
