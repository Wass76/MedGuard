package com.CareemSystem.object.Request;

import com.CareemSystem.object.Model.Maintenance;
import com.CareemSystem.object.Model.ModelPrice;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BicycleRequest {

    private ModelPriceRequest model_price;
    private Integer size;
//    private String type;
    private String note;
    private String type;

}
