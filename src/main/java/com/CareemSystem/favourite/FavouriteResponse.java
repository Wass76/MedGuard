package com.CareemSystem.favourite;

import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Response.BicycleResponse;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Response.ClientResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavouriteResponse {
    private Integer id;
    private BicycleResponse bicycle;
    private ClientResponse client;
}
