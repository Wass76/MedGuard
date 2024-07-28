package com.CareemSystem.policy;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
public class PolicyRequest {
    @NotBlank(message = "title can't be blank")
    private String title;
    @NotBlank(message = "description can't be blank")
    private String description;
}
