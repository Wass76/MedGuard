package com.CareemSystem.hub.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HubRequest {

    @NotBlank(message = "name couldn't be empty")
    private String name;
    @NotNull(message = "latitude couldn't be empty")
    private Double latitude;
    @NotNull(message = "longitude couldn't be empty")
    private Double longitude;
    @NotBlank(message = "description couldn't be empty")
    private String description;
}
