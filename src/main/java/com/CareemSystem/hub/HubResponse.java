package com.CareemSystem.hub;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HubResponse {
    private Integer id;
    private String name;
    private Float latitude;
    private Float longitude;
    private String description;
}
