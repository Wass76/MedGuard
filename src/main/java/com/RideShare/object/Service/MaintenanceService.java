package com.RideShare.object.Service;

import com.RideShare.Response.ApiResponseClass;
import com.RideShare.utils.exception.ApiRequestException;
import com.RideShare.object.Model.Maintenance;
import com.RideShare.object.Repository.BicycleRepository;
import com.RideShare.object.Repository.MaintenanceRepository;
import com.RideShare.object.Request.MaintenanceRequest;
import com.RideShare.object.Response.MaintenanceResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final BicycleRepository bicycleRepository;

    public ApiResponseClass getAllMaintenance() {
       List<Maintenance> maintenance = maintenanceRepository.findAll();
       List<MaintenanceResponse> responses = new ArrayList<>();
       for (Maintenance ma : maintenance) {
           responses.add(MaintenanceResponse.builder()
                   .id(ma.getId())
                   .cost(ma.getCost())
                   .note(ma.getNote())
                   .type(ma.getType())
                   .date(ma.getDate())
                   .bicycleId(ma.getBicycle().getId())
                   .description(ma.getDescription())
                   .build()
           );
       }
       return new ApiResponseClass("Get all maintenance done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),responses);
    }

    public ApiResponseClass getMaintenanceById(Integer id) {
        Maintenance ma = maintenanceRepository.findById(id).orElseThrow(
                () -> new ApiRequestException("maintenance not found")
        );
        MaintenanceResponse response = MaintenanceResponse.builder()
                .id(ma.getId())
                .cost(ma.getCost())
                .note(ma.getNote())
                .type(ma.getType())
                .date(ma.getDate())
                .bicycleId(ma.getBicycle().getId())
                .description(ma.getDescription())
                .build();
        return new ApiResponseClass("Get maintenance successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),response);
    }

    public ApiResponseClass getMaintenanceByBicycleId(Integer bicycleId) {

        List<Maintenance> maintenanceList = maintenanceRepository.findByBicycleId(bicycleId);
        List<MaintenanceResponse> responses = new ArrayList<>();
        for (Maintenance ma : maintenanceList) {
            responses.add(MaintenanceResponse.builder()
                     .id(ma.getId())
                    .cost(ma.getCost())
                    .note(ma.getNote())
                    .type(ma.getType())
                    .date(ma.getDate())
                    .bicycleId(ma.getBicycle().getId())
                    .description(ma.getDescription())
                    .build()
           );
        }
        return new ApiResponseClass("Get maintenances of bicycle done successfully" , HttpStatus.ACCEPTED , LocalDateTime.now(),responses);
    }

    @Transactional
    public ApiResponseClass createMaintenance(MaintenanceRequest request) {
        // TODO: make a type enum for maintenance
        Maintenance ma = Maintenance.builder()
                .date(request.getDate())
                .description(request.getDescription())
                .cost(request.getCost())
                .bicycle(bicycleRepository.findById(request.getBicycleId()).orElseThrow(
                        ()-> new ApiRequestException("bicycle with id: "+ request.getBicycleId() + "not found")
                ))
                .type(request.getType())
                .note(request.getNote())
                .build();

        MaintenanceResponse response = MaintenanceResponse.builder()
                .id(ma.getId())
                .cost(ma.getCost())
                .note(ma.getNote())
                .type(ma.getType())
                .date(ma.getDate())
                .bicycleId(ma.getBicycle().getId())
                .description(ma.getDescription())
                .build();

        return new ApiResponseClass(
                "Create maintenance successfully"
                , HttpStatus.CREATED ,
                LocalDateTime.now()
                ,response);
    }

}
