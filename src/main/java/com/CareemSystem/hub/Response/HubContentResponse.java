package com.CareemSystem.hub.Response;

import com.CareemSystem.object.Model.Bicycle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HubContentResponse {

    private Integer id;
    private Integer hubId;
    private List<Bicycle> bicycleList;
    private String note;
}
