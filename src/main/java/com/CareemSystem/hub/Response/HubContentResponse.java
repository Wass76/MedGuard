package com.CareemSystem.hub.Response;

import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Response.ClientBicycleResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HubContentResponse {

    private Integer id;
    private Integer hubId;
    private List<ClientBicycleResponse> bicycleList;
    private String note;
}
