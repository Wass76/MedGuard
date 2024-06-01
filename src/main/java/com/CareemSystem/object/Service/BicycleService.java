package com.CareemSystem.object.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Repository.BicycleRepository;
import com.CareemSystem.object.Request.BicycleRequest;
import com.CareemSystem.object.Response.BicycleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BicycleService {

    private final BicycleRepository bicycleRepository;

    public ApiResponseClass getAllObjects(){
       List<Bicycle> bicycleList = bicycleRepository.findAll();
       List<BicycleResponse> responseList = new ArrayList<>();
       for (Bicycle bicycle : bicycleList) {
           responseList.add(BicycleResponse.builder()
                   .id(bicycle.getId())
                   .type(bicycle.getType())
                   .size(bicycle.getSize())
                   .note(bicycle.getNote())
                   .model_price(bicycle.getModel_price())
                   .maintenance(bicycle.getMaintenance())
                   .build());
       }
       return new ApiResponseClass("Get all objects done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),responseList);
    }
    public ApiResponseClass getObjectById(int id){
        Bicycle bicycle = bicycleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Bicycle with id: " + id + "not found")
        );
        BicycleResponse response = BicycleResponse.builder()
                .id(bicycle.getId())
                .type(bicycle.getType())
                .size(bicycle.getSize())
                .note(bicycle.getNote())
                .model_price(bicycle.getModel_price())
                .maintenance(bicycle.getMaintenance())
                .build();
        return new ApiResponseClass("Get object by id" , HttpStatus.ACCEPTED , LocalDateTime.now(),response);
    }
    public ApiResponseClass createObject(BicycleRequest request){
        Bicycle bicycle = Bicycle.builder()
                .note(request.getNote())
                .size(request.getSize())
                .model_price(request.getModel_price())
                .type(request.getType())
                .build();

        bicycleRepository.save(bicycle);

        BicycleResponse response = BicycleResponse.builder()
                .id(bicycle.getId())
                .type(bicycle.getType())
                .size(bicycle.getSize())
                .note(bicycle.getNote())
                .model_price(bicycle.getModel_price())
                .maintenance(bicycle.getMaintenance())
                .build();

        return new ApiResponseClass("Create object" , HttpStatus.CREATED , LocalDateTime.now(),response);
    }

}
