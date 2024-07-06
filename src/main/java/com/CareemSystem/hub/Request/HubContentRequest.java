package com.CareemSystem.hub.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HubContentRequest {

//    @NotNull(message = "hub_id can't be null")
//    private Integer hub_id;
    @NotNull(message = "bicycle id can't be null")
    private List<Integer> bicycles;

    private String note;
}
