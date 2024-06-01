package com.CareemSystem.object.Response;

import com.CareemSystem.object.Model.Maintenance;
import com.CareemSystem.object.Model.ModelPrice;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class BicycleResponse
{
    private Integer id;
    private ModelPrice model_price;
    private Integer size;
    private String type;
    private String note;
    private List<Maintenance> maintenance;

}
