package com.RideShare.favourite;

import com.RideShare.object.Response.ClientBicycleResponse;
import com.RideShare.user.Response.ClientResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavouriteResponse {
    private Integer id;
    private ClientBicycleResponse bicycle;
    private ClientResponse client;
}
