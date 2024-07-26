package com.CareemSystem.hub.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.utils.Validator.ObjectsValidator;
import com.CareemSystem.utils.exception.ApiRequestException;
import com.CareemSystem.hub.Entity.HubContent;
import com.CareemSystem.hub.Repository.HubContentRepository;
import com.CareemSystem.hub.Repository.HubRepository;
import com.CareemSystem.hub.Request.HubContentRequest;
import com.CareemSystem.hub.Response.HubContentResponse;
import com.CareemSystem.object.Enum.BicycleCategory;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Repository.BicycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HubContentService {

    private final HubContentRepository hubContentRepository;
    private final ObjectsValidator<HubContentRequest> hubContentValidator;
    private final HubRepository hubRepository;
    private final BicycleRepository bicycleRepository;

    public ApiResponseClass getHubContentByHubId(Integer hubId , String category) {
        try {
            BicycleCategory.valueOf(category);
        }catch (IllegalArgumentException e){
            throw new ApiRequestException("category name is invalid");
        }
        HubContent hubContent = hubContentRepository.findContentByBicycleTypeAndHubId(
                hubId,
                BicycleCategory.valueOf(category)
        ).orElseThrow(
                ()-> new ApiRequestException("hub content does not exist, or there is no bicycles with category " +"(" + category + ")" + " in this hub")
        );
        HubContentResponse hubContentResponse = HubContentResponse.builder()
                .id(hubContent.getId())
                .hubId(hubContent.getHub().getId())
                .bicycleList(hubContent.getBicycles())
                .note(hubContent.getNote())
                .build();

        return new ApiResponseClass("Get Hub content by id done successfully" , HttpStatus.OK, LocalDateTime.now(),hubContentResponse);
    }

//    public ApiResponseClass createHubContent( HubContentRequest request) {
//        hubContentValidator.validate(request);
//
//        Hub hub = hubRepository.findById(request.getHub_id()).orElseThrow(
//                ()-> new ApiRequestException("hub does not exist")
//        );
//        List<Bicycle> bicycles = new ArrayList<>();
//        for(Integer bicycle : request.getBicycles()){
//            bicycles.add(bicycleRepository.findById(bicycle).orElseThrow(
//                    ()-> new ApiRequestException("bicycle with id: "+ bicycle + "does not exist")
//            ));
//        }
//
//        HubContent hubContent = HubContent.builder()
//                .hub(hub)
//                .bicycles(bicycles)
//                .note(request.getNote())
//                .build();
//        hubContentRepository.save(hubContent);
//
//        HubContentResponse hubContentResponse = HubContentResponse.builder()
//                .id(hubContent.getId())
//                .hubId(hubContent.getHub().getId())
//                .bicycleList(hubContent.getBicycles())
//                .note(hubContent.getNote())
//                .build();
//
//        return new ApiResponseClass("Create hub content successfully" , HttpStatus.CREATED, LocalDateTime.now(),hubContentResponse);
//    }

    public ApiResponseClass updateHubContent(Integer hubId, HubContentRequest request) {
        hubContentValidator.validate(request);

        HubContent hubContent = hubContentRepository.findById(hubId).orElseThrow(
                ()-> new ApiRequestException("hub with this id does not exist")
        );

        List<Bicycle> bicycles = new ArrayList<>();
        for(Integer bicycle : request.getBicycles()){
            bicycles.add(bicycleRepository.findById(bicycle).orElseThrow(
                    ()-> new ApiRequestException("bicycle with id: "+ bicycle + "does not exist")
            ));
        }
        hubContent.setNote(request.getNote());
//        hubContent.setHub(hub);
        hubContent.setBicycles(bicycles);
        hubContentRepository.save(hubContent);

        HubContentResponse hubContentResponse = HubContentResponse.builder()
                .id(hubContent.getId())
                .hubId(hubContent.getHub().getId())
                .bicycleList(hubContent.getBicycles())
                .note(hubContent.getNote())
                .build();

        return new ApiResponseClass("Update hub content successfully" , HttpStatus.OK, LocalDateTime.now(),hubContentResponse);
    }

    public ApiResponseClass deleteHubContent(Integer id) {
            HubContent hubContent = hubContentRepository.findById(id).orElseThrow(
                    ()-> new ApiRequestException("hub content does not exist")
            );
            hubContentRepository.delete(hubContent);
            return new ApiResponseClass("Delete hub content successfully" , HttpStatus.NO_CONTENT, LocalDateTime.now());
    }
}
