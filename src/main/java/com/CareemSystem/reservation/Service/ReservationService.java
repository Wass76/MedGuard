package com.CareemSystem.reservation.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.Validator.ObjectsValidator;
import com.CareemSystem.exception.ApiRequestException;
import com.CareemSystem.hub.Entity.Hub;
import com.CareemSystem.hub.Repository.HubRepository;
import com.CareemSystem.object.Model.Bicycle;
import com.CareemSystem.object.Repository.BicycleRepository;
import com.CareemSystem.reservation.Enum.ReservationStatus;
import com.CareemSystem.reservation.Repository.ReservationRepository;
import com.CareemSystem.reservation.Model.Reservation;
import com.CareemSystem.reservation.Request.ReservationRequest;
import com.CareemSystem.reservation.Response.ReservationResponse;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public ApiResponseClass createReservation(ReservationRequest request) {
        validator.validate(request);

        Client client = clientRepository.findById(request.getClientId()).orElseThrow(
                ()-> new ApiRequestException("Client not found"));
        Bicycle bicycle = bicycleRepository.findById(request.getBicycleId()).orElseThrow(
                ()-> new ApiRequestException("Bicycle not found"));
        Hub fromHub = hubRepository.findById(request.getFromHubId()).orElseThrow(
                ()-> new ApiRequestException("FromHub not found"));
        Hub toHub = hubRepository.findById(request.getToHubId()).orElseThrow(
                ()-> new ApiRequestException("ToHub not found"));


        //TODO : check if other reservation exist
        List<Reservation> overlappingReservations = reservationRepository
                .findOverlappingReservations(
                        request.getStartTime()
                        , request.getEndTime()
                );
        if (!overlappingReservations.isEmpty()) {
            return new ApiResponseClass("Reserved, Try another Time",HttpStatus.BAD_REQUEST,LocalDateTime.now());
        }


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

        reservationRepository.save(reservation);

        ReservationResponse response = ReservationResponse.builder()
                .id(reservation.getId())
                .client(reservation.getClient().get_username())
                .bicycle(reservation.getBicycle().getModel_price().getModel())
                .from(reservation.getFrom().getName())
                .to(reservation.getTo().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .duration(reservation.getDuration())
                .reservationStatus(reservation.getReservationStatus().name())
                .build();

        return new ApiResponseClass("Reservation created successfully", HttpStatus.CREATED, LocalDateTime.now(),response);
    }

    @Transactional
    public ApiResponseClass updateReservationToDuringReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new ApiRequestException("Reservation not found"));
        reservation.setReservationStatus(ReservationStatus.DURING_RESERVATION);
        reservationRepository.save(reservation);
        ReservationResponse response = ReservationResponse.builder()
                .id(reservation.getId())
                .client(reservation.getClient().get_username())
                .bicycle(reservation.getBicycle().getModel_price().getModel())
                .from(reservation.getFrom().getName())
                .to(reservation.getTo().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .duration(reservation.getDuration())
                .reservationStatus(reservation.getReservationStatus().name())
                .build();
        return new ApiResponseClass("Reservation updated successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }
    @Transactional
    public ApiResponseClass updateReservationToFinished(Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new ApiRequestException("Reservation not found"));
        reservation.setReservationStatus(ReservationStatus.FINISHED);
        reservationRepository.save(reservation);
        ReservationResponse response = ReservationResponse.builder()
                .id(reservation.getId())
                .client(reservation.getClient().get_username())
                .bicycle(reservation.getBicycle().getModel_price().getModel())
                .from(reservation.getFrom().getName())
                .to(reservation.getTo().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .duration(reservation.getDuration())
                .reservationStatus(reservation.getReservationStatus().name())
                .build();
        return new ApiResponseClass("Reservation updated successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass getReservation(Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                ()-> new ApiRequestException("Reservation not found")
        );

        ReservationResponse response = ReservationResponse.builder()
                .id(reservation.getId())
                .client(reservation.getClient().get_username())
                .bicycle(reservation.getBicycle().getModel_price().getModel())
                .from(reservation.getFrom().getName())
                .to(reservation.getTo().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .duration(reservation.getDuration())
                .reservationStatus(reservation.getReservationStatus().name())
                .build();

        return new ApiResponseClass("Reservation get successfully", HttpStatus.OK, LocalDateTime.now(),response);
    }

    public ApiResponseClass getAllReservationsByBicycleId(Integer bicycleId) {
        Bicycle bicycle= bicycleRepository.findById(bicycleId).orElseThrow(()-> new ApiRequestException("Bicycle not found"));
        List<Reservation> reservations = reservationRepository.findByBicycleId(bicycleId);
        if(reservations.isEmpty())
            return new ApiResponseClass("No Reservations found", HttpStatus.NO_CONTENT,LocalDateTime.now());

        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse response = ReservationResponse.builder()
                    .id(reservation.getId())
                    .client(reservation.getClient().get_username())
                    .bicycle(reservation.getBicycle().getModel_price().getModel())
                    .from(reservation.getFrom().getName())
                    .to(reservation.getTo().getName())
                    .startTime(reservation.getStartTime())
                    .endTime(reservation.getEndTime())
                    .duration(reservation.getDuration())
                    .reservationStatus(reservation.getReservationStatus().name())
                    .build();
            responses.add(response);
        }
        return new ApiResponseClass("All reservations found", HttpStatus.OK, LocalDateTime.now(),responses);
    }

    public ApiResponseClass getAllReservationsByClientId(Integer clientId) {

        List<Reservation> reservations = reservationRepository.findByClientId(clientId);

        if(reservations.isEmpty()) return new ApiResponseClass("No Reservations found", HttpStatus.OK,LocalDateTime.now());

        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse response = ReservationResponse.builder()
                    .id(reservation.getId())
                    .client(reservation.getClient().get_username())
                    .bicycle(reservation.getBicycle().getModel_price().getModel())
                    .from(reservation.getFrom().getName())
                    .to(reservation.getTo().getName())
                    .startTime(reservation.getStartTime())
                    .endTime(reservation.getEndTime())
                    .duration(reservation.getDuration())
                    .reservationStatus(reservation.getReservationStatus().name())
                    .build();
            responses.add(response);
        }
        return new ApiResponseClass("All reservations found", HttpStatus.OK, LocalDateTime.now(),responses);
    }

    public ApiResponseClass deleteReservation(Integer id) {
        reservationRepository.delete(reservationRepository.findById(id).orElseThrow(()-> new ApiRequestException("Reservation not found")));
        return new ApiResponseClass("Reservation Cancelled successfully", HttpStatus.OK, LocalDateTime.now());
    }

}
