package com.RideShare.object.Response;

import com.RideShare.object.Model.Maintenance;
import com.RideShare.object.Model.ModelPrice;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ClientBicycleResponse {

    private Integer id;
    private ModelPrice model_price;
    private Integer size;
    private String photoPath;
    private String type;
    private String note;
    private List<Maintenance> maintenance;
    private Boolean isFavourite;
}
