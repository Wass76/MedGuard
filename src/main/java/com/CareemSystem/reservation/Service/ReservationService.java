package com.CareemSystem.reservation.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.Validator.ObjectsValidator;
import com.CareemSystem.exception.ApiRequestException;
import com.CareemSystem.hub.Hub;
import com.CareemSystem.hub.HubRepository;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Repository.BicycleRepository;
import com.CareemSystem.reservation.Enum.ReservationStatus;
import com.CareemSystem.reservation.Repository.ReservationRepository;
import com.CareemSystem.reservation.Model.Reservation;
import com.CareemSystem.reservation.Request.ReservationRequest;
import com.CareemSystem.reservation.Response.ReservationResponse;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Repository.ClientRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Builder
@Data
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final BicycleRepository bicycleRepository;
    private final HubRepository hubRepository;
    private final ObjectsValidator<ReservationRequest> validator;

    public ApiResponseClass createReservation(ReservationRequest request) {
        validator.validate(request);
        Client client = clientRepository.findById(request.getClientId()).orElseThrow(()-> new ApiRequestException("Client not found"));
        Bicycle bicycle = bicycleRepository.findById(request.getBicycleId()).orElseThrow(()-> new ApiRequestException("Bicycle not found"));
        Hub fromHub = hubRepository.findById(request.getFromHubId()).orElseThrow(()-> new ApiRequestException("FromHub not found"));
        Hub toHub = hubRepository.findById(request.getToHubId()).orElseThrow(()-> new ApiRequestException("ToHub not found"));


        //TODO : check if other reservation exist

        Reservation reservation = Reservation.builder()
                .client(client)
                .bicycle(bicycle)
                .from(fromHub)
                .to(toHub)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .duration(request.getDuration())
                .reservationStatus(ReservationStatus.NOT_STARTED)
                .build();


        ReservationResponse response = ReservationResponse.builder()
                .id(reservation.getId())
                .client(reservation.getClient())
                .bicycle(reservation.getBicycle())
                .from(reservation.getFrom())
                .to(reservation.getTo())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .duration(reservation.getDuration())
                .reservationStatus(reservation.getReservationStatus().name())
                .build();

        return new ApiResponseClass("Reservation created successfully", HttpStatus.CREATED, LocalDateTime.now(),response);
    }



}
