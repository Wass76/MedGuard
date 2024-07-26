package com.CareemSystem.reservation.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.utils.Validator.ObjectsValidator;
import com.CareemSystem.utils.exception.ApiRequestException;
import com.CareemSystem.hub.Entity.Hub;
import com.CareemSystem.hub.Entity.HubContent;
import com.CareemSystem.hub.Repository.HubContentRepository;
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
import com.CareemSystem.wallet.Enum.PaymentMethod;
import com.CareemSystem.wallet.Repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final BicycleRepository bicycleRepository;
    private final HubRepository hubRepository;
    private final ObjectsValidator<ReservationRequest> validator;
    private final HubContentRepository hubContentRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public ApiResponseClass createReservation(ReservationRequest request, Principal principal) {
        validator.validate(request);

        Client reservationOwner = (Client) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        Client client = clientRepository.findById(reservationOwner.getId()).orElseThrow(
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

        Double reservationPrice = bicycle.getModel_price().getPrice() * request.getDuration();
        Double walletBalance = client.getWallet().getBalance();

        if(request.getPaymentMethod().equals(PaymentMethod.Wallet)){
            if(walletBalance < reservationPrice){
                 throw new ApiRequestException("Not enough wallet, please charge your wallet first then try again");
            }
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


        client.getWallet().setBalance(walletBalance - reservationPrice);
        walletRepository.save(client.getWallet());
        clientRepository.save(client);
        System.out.println(client.getWallet().getBalance());

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
                .price(reservationPrice)
                .build();

        return new ApiResponseClass("Reservation created successfully", HttpStatus.CREATED, LocalDateTime.now(),response);
    }

    @Transactional
    public ApiResponseClass updateReservationToDuringReservation(Integer id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                ()-> new ApiRequestException("Reservation not found")
        );
        reservation.setReservationStatus(ReservationStatus.DURING_RESERVATION);
        reservationRepository.save(reservation);
        HubContent hubContentFrom = hubContentRepository.findByHubId(reservation.getFrom().getId()).orElseThrow(
                ()-> new ApiRequestException("hub not found")
        );
        HubContent hubContentTo = hubContentRepository.findByHubId(reservation.getTo().getId()).orElseThrow(
                ()-> new ApiRequestException("hub not found")
        );
        List<Bicycle> bicycleListFrom = hubContentFrom.getBicycles();
        for (Bicycle bicycle : bicycleListFrom) {
            if(bicycle.getId().equals(reservation.getBicycle().getId())) {
                hubContentFrom.getBicycles().remove(bicycle);
                hubContentTo.getBicycles().add(bicycle);
            }
        }

        hubContentRepository.save(hubContentFrom);
        hubContentRepository.save(hubContentTo);

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
                .price(reservation.getDuration() * reservation.getBicycle().getModel_price().getPrice())
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
                .price(reservation.getDuration() * reservation.getBicycle().getModel_price().getPrice())
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
                .price(reservation.getDuration() * reservation.getBicycle().getModel_price().getPrice())
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
                    .price(reservation.getDuration() * reservation.getBicycle().getModel_price().getPrice())
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
                    .price(reservation.getDuration() * reservation.getBicycle().getModel_price().getPrice())
                    .build();
            responses.add(response);
        }
        return new ApiResponseClass("All reservations found", HttpStatus.OK, LocalDateTime.now(),responses);
    }

    public ApiResponseClass deleteReservation(Integer id) {
       Reservation reservation =  reservationRepository.findById(id).orElseThrow(
                ()-> new ApiRequestException("Reservation not found")
       );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByPhone(authentication.getName()).orElseThrow(
                ()-> new ApiRequestException("Client not found")
        );
        Double reservationPrice = reservation.getDuration() * reservation.getBicycle().getModel_price().getPrice();
        Double walletBalance = client.getWallet().getBalance();
        if (client.get_username().equals(reservation.getClient().get_username())) {

            if (reservation.getReservationStatus().equals(ReservationStatus.NOT_STARTED)){
                client.getWallet().setBalance(walletBalance + reservationPrice);
//                clientRepository.save(client);
                walletRepository.save(client.getWallet());
                reservationRepository.delete(reservation);
            }
            else {
                throw new ApiRequestException("You are not allowed to delete a reservation that is already in progress");
            }
        }
        else {
            throw new IllegalArgumentException("You can't cancel a reservation you didn't book");
        }

        return new ApiResponseClass("Reservation Cancelled successfully", HttpStatus.OK, LocalDateTime.now());
    }

}
