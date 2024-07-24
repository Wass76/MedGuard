package com.CareemSystem.favourite;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavouriteRequest {

//    @NotNull(message = "clientId can't be null")
//    private Integer clientId;
    @NotNull(message = "bicycleId can't be null")
    @JsonProperty
    private Integer bicycleId;
}
