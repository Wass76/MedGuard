package com.CareemSystem.object.Request;

import com.CareemSystem.object.Model.Maintenance;
import com.CareemSystem.object.Model.ModelPrice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
