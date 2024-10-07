package com.RideShare.object.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BicycleRequest {

    private ModelPriceRequest model_price;
    @NotNull(message ="can't be null" )
    private Integer size;
//    private String type;
    private String note;
    @NotBlank(message = "Can't be null")
    private String type;
    private MultipartFile photo;

}
