package com.RideShare.hub.Response;

import com.RideShare.object.Response.ClientBicycleResponse;
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
