package com.CareemSystem.hub;

import com.CareemSystem.Response.ApiResponseClass;
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

    public ApiResponseClass getAllHubs(){
        List<Hub> hubList =  hubRepository.findAll();
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
        return new ApiResponseClass("Get all places done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(), responseList);
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
        return new ApiResponseClass("Hub not found" , HttpStatus.ACCEPTED , LocalDateTime.now());
    }


}
