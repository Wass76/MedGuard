package com.CareemSystem.hub.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.Validator.ObjectsValidator;
import com.CareemSystem.exception.ApiRequestException;
import com.CareemSystem.hub.Entity.Hub;
import com.CareemSystem.hub.Repository.HubRepository;
import com.CareemSystem.hub.Response.HubResponse;
import com.CareemSystem.hub.Request.HubRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HubService {

    @Autowired
    private HubRepository hubRepository;

    @Autowired
    private ObjectsValidator<HubRequest> validator;

    public ApiResponseClass getAllHubs(Double longitude, Double latitude){

//        TODO: Radius of Earth is = 6371
        List<Hub> hubList = hubRepository.findByLocation( latitude,longitude );

        List<HubResponse> responseList = new ArrayList<>();
        for(Hub hub : hubList){
            responseList.add(HubResponse.builder()
                    .id(hub.getId())
                    .name(hub.getName())
                    .description(hub.getDescription())
                    .latitude(hub.getLatitude())
                    .longitude(hub.getLongitude())
                    .build());
        }
        return new ApiResponseClass("Get all places done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),responseList);
    }

    public ApiResponseClass getHubById(Integer id){
        Optional<Hub> hub = hubRepository.findById(id);
        if(hub.isPresent()){
            HubResponse response = HubResponse.builder()
                    .id(hub.get().getId())
                    .name(hub.get().getName())
                    .description(hub.get().getDescription())
                    .latitude(hub.get().getLatitude())
                    .longitude(hub.get().getLongitude())
                    .build();
             return new ApiResponseClass("Get all places done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(), response);
        }
        throw new ApiRequestException("Hub with id " + id + " not found");
//        return new ApiResponseClass("Hub not found" , HttpStatus.ACCEPTED , LocalDateTime.now());
    }

    @Transactional
    public ApiResponseClass createHub(HubRequest request){
        validator.validate(request);
        Hub hub = Hub.builder()
                .name(request.getName())
                .description(request.getDescription())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .build();
        hubRepository.save(hub);

        HubResponse response = HubResponse.builder()
                .id(hub.getId())
                .name(hub.getName())
                .description(hub.getDescription())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .build();

        return new ApiResponseClass("Create new hub successfully" , HttpStatus.CREATED , LocalDateTime.now(), response);
    }

    public ApiResponseClass updateHub(Integer id, HubRequest request){
        Optional<Hub> hub = hubRepository.findById(id);
        if(!hub.isPresent()){
            throw new ApiRequestException("Hub with id " + id + " not found");
        }
        validator.validate(request);
        hub.get().setName(request.getName());
        hub.get().setDescription(request.getDescription());
//        hub.get().setLatitude(request.getLatitude());
//        hub.get().setLongitude(request.getLongitude());
        hubRepository.save(hub.get());

        HubResponse response = HubResponse.builder()
                .id( hub.get().getId())
                .name( hub.get().getName())
                .description( hub.get().getDescription())
                .latitude(hub.get().getLatitude())
                .longitude(hub.get().getLongitude())
                .build();

        return new ApiResponseClass("Update hub successfully" , HttpStatus.ACCEPTED , LocalDateTime.now() , response);
    }
    public ApiResponseClass deleteHub(Integer id){
        Optional<Hub> hub = hubRepository.findById(id);
        if(!hub.isPresent()){
            throw new ApiRequestException("Hub with id " + id + " not found");
        }
        hubRepository.delete(hub.get());
        return new ApiResponseClass("Delete hub successfully" , HttpStatus.NO_CONTENT , LocalDateTime.now());
    }


}
