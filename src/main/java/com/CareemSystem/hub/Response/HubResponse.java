package com.CareemSystem.hub.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HubResponse {
    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String description;
}
