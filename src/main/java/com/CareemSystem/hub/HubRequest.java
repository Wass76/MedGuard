package com.CareemSystem.hub;

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
    private Float latitude;
    @NotNull(message = "longitude couldn't be empty")
    private Float longitude;
    @NotBlank(message = "description couldn't be empty")
    private String description;
}
