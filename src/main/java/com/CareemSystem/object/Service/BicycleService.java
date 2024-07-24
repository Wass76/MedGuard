package com.CareemSystem.object.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.object.Enum.BicycleCategory;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Model.ModelPrice;
import com.CareemSystem.object.Repository.BicycleRepository;
import com.CareemSystem.object.Repository.ModelPriceRepository;
import com.CareemSystem.object.Request.BicycleRequest;
import com.CareemSystem.object.Response.BicycleResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BicycleService {

    private final BicycleRepository bicycleRepository;
    private final ModelPriceRepository modelPriceRepository;

    public ApiResponseClass getAllObjects(){
       List<Bicycle> bicycleList = bicycleRepository.findAll();
       List<BicycleResponse> responseList = new ArrayList<>();
       for (Bicycle bicycle : bicycleList) {
           responseList.add(BicycleResponse.builder()
                   .id(bicycle.getId())
                   .type(bicycle.getType().toString())
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
                .type(bicycle.getType().toString())
                .size(bicycle.getSize())
                .note(bicycle.getNote())
//                .category(bicycle.getCategory().toString())
                .model_price(bicycle.getModel_price())
                .maintenance(bicycle.getMaintenance())
                .build();
        return new ApiResponseClass("Get object by id" , HttpStatus.ACCEPTED , LocalDateTime.now(),response);
    }

    public ApiResponseClass getObjectByCategory(String name){
        List<Bicycle> bicycleList = bicycleRepository.findBicycleByType(BicycleCategory.valueOf(name));
        List<BicycleResponse> responseList = new ArrayList<>();

        for (Bicycle bicycle : bicycleList) {
            responseList.add(BicycleResponse.builder()
                    .id(bicycle.getId())
                    .type(bicycle.getType().toString())
                    .size(bicycle.getSize())
//                    .category(bicycle.getType().toString())
                    .note(bicycle.getNote())
                    .model_price(bicycle.getModel_price())
                    .maintenance(bicycle.getMaintenance())
                    .build());
        }
        return new ApiResponseClass("Get bicycles by category" , HttpStatus.ACCEPTED , LocalDateTime.now(),responseList);

    }

    public ApiResponseClass getAllCategories(){
//        BicycleCategory[] bicycleCategories = BicycleCategory.values();
        List<BicycleCategory> bicycleCategoriesList = List.of(BicycleCategory.values());
//        List<BicycleCategory> bicycleCategoriesList = new ArrayList<>(Arrays.asList(bicycleCategories));

        return new ApiResponseClass("Get all categories", HttpStatus.ACCEPTED , LocalDateTime.now(),bicycleCategoriesList);
    }

    @Transactional
    public ApiResponseClass createObject(BicycleRequest request){

        ModelPrice modelPrice = ModelPrice.builder()
                .model(request.getModel_price().getModelName())
                .price(request.getModel_price().getPrice())
                .build();
        modelPriceRepository.save(modelPrice);

        Bicycle bicycle = Bicycle.builder()
                .note(request.getNote())
                .size(request.getSize())
                .model_price(modelPrice)
//                .category(BicycleCategory.valueOf(request.getCategory()))
                .type(BicycleCategory.valueOf(request.getType()))
                .build();

        bicycleRepository.save(bicycle);

        BicycleResponse response = BicycleResponse.builder()
                .id(bicycle.getId())
                .type(bicycle.getType().toString())
                .size(bicycle.getSize())
                .note(bicycle.getNote())
//                .category(bicycle.getCategory().toString())
                .model_price(bicycle.getModel_price())
                .maintenance(bicycle.getMaintenance())
                .build();

        return new ApiResponseClass("Create bicycle done successfully" , HttpStatus.CREATED , LocalDateTime.now(),response);
    }

    @Transactional
    public ApiResponseClass updateObject(Integer id, BicycleRequest request){
        Bicycle bicycle = bicycleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Bicycle with id: " + id + "not found")
        );

        ModelPrice modelPrice = modelPriceRepository.findByModel(bicycle.getModel_price().getModel()).orElseThrow(
                ()-> new RuntimeException("ModelPrice with id: " + id + "not found")
        );
        modelPrice.setModel(request.getModel_price().getModelName());
        modelPrice.setPrice(request.getModel_price().getPrice());
        modelPriceRepository.save(modelPrice);

        bicycle.setNote(request.getNote());
        bicycle.setSize(request.getSize());
        bicycle.setModel_price(modelPrice);
//        bicycle.setCategory(BicycleCategory.valueOf(request.getCategory()));
        bicycle.setType(BicycleCategory.valueOf(request.getType()));
        bicycleRepository.save(bicycle);

        BicycleResponse response = BicycleResponse.builder()
                .id(bicycle.getId())
                .type(bicycle.getType().toString())
                .size(bicycle.getSize())
                .note(bicycle.getNote())
//                .category(bicycle.getCategory().toString())
                .model_price(bicycle.getModel_price())
                .maintenance(bicycle.getMaintenance())
                .build();
        return new ApiResponseClass("Update bicycle with id: " + id + " done successfully", HttpStatus.ACCEPTED , LocalDateTime.now(),response);
    }

    public ApiResponseClass deleteObject(Integer id){
        Bicycle bicycle = bicycleRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Bicycle with id: " + id + "not found")
        );
        bicycleRepository.delete(bicycle);
        return new ApiResponseClass("Delete bicycle with id: " + id + " done successfully" , HttpStatus.NO_CONTENT,LocalDateTime.now());
    }

    public ApiResponseClass getOfferBicycles(){
        List<Bicycle> bicycleList = bicycleRepository.findBicyclesByDiscountPriceTrue();
        if(bicycleList.isEmpty()){
            return new ApiResponseClass("There is no offers now", HttpStatus.NO_CONTENT , LocalDateTime.now());
        }
       List<BicycleResponse> response = new ArrayList<>();
        for(Bicycle bicycle : bicycleList){
            response.add(BicycleResponse.builder()
                    .id(bicycle.getId())
                    .type(bicycle.getType().toString())
                    .size(bicycle.getSize())
                    .note(bicycle.getNote())
                    .model_price(bicycle.getModel_price())
                    .maintenance(bicycle.getMaintenance())
                    .build());
        }
        return new ApiResponseClass("Get offers", HttpStatus.OK , LocalDateTime.now(),response);
    }


}
