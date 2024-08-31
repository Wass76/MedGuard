package com.CareemSystem.reservation.Service;

import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.user.Model.BaseUser;
import com.CareemSystem.utils.Service.UtilsService;
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
import com.CareemSystem.wallet.Request.PaymentRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BicycleRepository bicycleRepository;
    @Autowired
    private HubRepository hubRepository;
    @Autowired
    private ObjectsValidator<ReservationRequest> validator;
    @Autowired
    private HubContentRepository hubContentRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private ObjectsValidator<PaymentRequest> paymentRequestValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean isOverlappingCheck( Integer duration,
            LocalDateTime startTime
//            ReservationRequest request
    ) {
        LocalDateTime finishTime = startTime.plusHours(duration);
        System.out.println(duration);
        System.out.println(finishTime);
        List<Reservation> overlappingReservations = reservationRepository
                .findOverlappingReservations(
                        startTime
                        , finishTime
                );
        if (!overlappingReservations.isEmpty()) {
            return true;
        }
        return false;
    }

    @Transactional
    public ApiResponseClass createReservation(ReservationRequest request) {
        validator.validate(request);

        var reservationOwner = utilsService.extractCurrentUser();
        if(reservationOwner instanceof Client) {

        Client client = clientRepository.findById(reservationOwner.getId()).orElseThrow(
                ()-> new ApiRequestException("Client not found"));
//        if(client.getWallet() == null){
//            throw new ApiRequestException("YOU DON'T HAVE WALLET, PLEASE CREATE WALLET FIRST");
//        }
        Bicycle bicycle = bicycleRepository.findById(request.getBicycleId()).orElseThrow(
                ()-> new ApiRequestException("Bicycle not found"));
        Hub fromHub = hubRepository.findById(request.getFromHubId()).orElseThrow(
                ()-> new ApiRequestException("Source hub not found"));
        Hub toHub = hubRepository.findById(request.getToHubId()).orElseThrow(
                ()-> new ApiRequestException("Target hub not found"));


        //TODO : check if other reservation exist // Done //
            if (isOverlappingCheck((int)request.getDuration(),request.getStartTime())) {
                return new ApiResponseClass("Reserved, Try another Time",HttpStatus.BAD_REQUEST,LocalDateTime.now());
            }

        Double reservationPrice = bicycle.getModel_price().getPrice() * request.getDuration();
//        Double walletBalance = client.getWallet().getBalance();

        HubContent hubContent = hubContentRepository.findById(request.getFromHubId()).orElseThrow(
                ()-> new ApiRequestException("Source hubContent not found")
        );
        Bicycle bicycleInSource = null;
        for (Bicycle b1 : hubContent.getBicycles()){
            if(b1.getId().equals(request.getBicycleId())){
                bicycleInSource = b1;
                break;
            }
        }
        if(bicycleInSource == null){
            throw new ApiRequestException("Bicycle not found in source");
        }


//        if(request.getPaymentMethod().equals(PaymentMethod.Wallet)){
//            if(walletBalance < reservationPrice){
//                throw new ApiRequestException("Not enough wallet, please charge your wallet first then try again");
//            }
//        }

        Reservation reservation = Reservation.builder()
                .client(client)
                .bicycle(bicycle)
                .from(fromHub)
                .to(toHub)
                .startTime(request.getStartTime())
                .endTime(null)
                .duration(request.getDuration())
                .reservationStatus(ReservationStatus.PENDING)
                .paymentMethod(PaymentMethod.Wallet)
                .build();

        reservationRepository.save(reservation);


//        client.getWallet().setBalance(walletBalance - reservationPrice);
//        walletRepository.save(client.getWallet());
//        clientRepository.save(client);
//        System.out.println(client.getWallet().getBalance());

        ReservationResponse response = ReservationResponse.builder()
                .id(reservation.getId())
                .client(reservation.getClient().get_username())
                .bicycle(reservation.getBicycle().getModel_price().getModel())
                .from(reservation.getFrom().getName())
                .to(reservation.getTo().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .duration(reservation.getDuration())
                .reservationStatus(String.valueOf(reservation.getReservationStatus()))
                .price(reservationPrice)
                .build();

        return new ApiResponseClass("Reservation created, but now it's in PENDING status, Complete payment processing to confirm your reservation", HttpStatus.CREATED, LocalDateTime.now(),response);
    }
        else {
            throw new ApiRequestException("This account not a client account " + reservationOwner.get_username());
        }
    }

    @Autowired
    private UtilsService utilsService;

    @Transactional
    public ApiResponseClass updateReservationToDuringReservation(Integer id) {

        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                ()-> new ApiRequestException("Reservation not found")
        );
        if(reservation.getReservationStatus().equals(ReservationStatus.PENDING)){
            throw new ApiRequestException("This reservation not confirmed yet");
        }
        if(reservation.getReservationStatus().equals(ReservationStatus.FINISHED)){
            throw new ApiRequestException("This reservation has already finished");
        }
        reservation.setReservationStatus(ReservationStatus.DURING_RESERVATION);
        reservationRepository.save(reservation);
        reservationRepository.flush();

    // Transfer bicycle from source hub to destination hub
        HubContent sourceHubContent = hubContentRepository.findByHubId(reservation.getFrom().getId()).orElseThrow(
                ()-> new ApiRequestException("source hub not found")
        );
        HubContent targetHubContent = hubContentRepository.findByHubId(reservation.getTo().getId()).orElseThrow(
                ()-> new ApiRequestException("destination hub not found")
        );

        Bicycle bicycleToMove = sourceHubContent.getBicycles().stream()
                .filter(b -> b.getId().equals(reservation.getBicycle().getId()))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Bicycle not found in source hub: " + reservation.getFrom().getName())
                );
//        System.out.println(bicycleToMove.getId());

        // Remove the Bicycle from the source HubContent
        reservation.setBicycle(null);
        sourceHubContent.getBicycles().remove(bicycleToMove);
        targetHubContent.getBicycles().add(bicycleToMove);
        reservation.setBicycle(bicycleToMove);
        reservationRepository.save(reservation);
        hubContentRepository.save(sourceHubContent);
        hubContentRepository.save(targetHubContent);

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

        if(! ((reservation.getReservationStatus().equals(ReservationStatus.NOT_STARTED)) || (reservation.getReservationStatus().equals(ReservationStatus.DURING_RESERVATION)) )){
            throw new ApiRequestException("Reservation not confirmed yet");
        }
//        reservation.setReservationStatus(ReservationStatus.FINISHED);
//        reservation.setEndTime(LocalDateTime.now());
//        reservationRepository.save(reservation);

        HubContent sourceHubContent = hubContentRepository.findByHubId(reservation.getFrom().getId()).orElseThrow(
                ()-> new ApiRequestException("source hub not found")
        );
        HubContent targetHubContent = hubContentRepository.findByHubId(reservation.getTo().getId()).orElseThrow(
                ()-> new ApiRequestException("destination hub not found")
        );

        System.out.println(reservation.getReservationStatus());
        if(!(reservation.getReservationStatus().equals(ReservationStatus.DURING_RESERVATION))){
            Bicycle bicycleToMove =
                    sourceHubContent.getBicycles().stream()
                    .filter(b -> b.getId().equals(reservation.getBicycle().getId()))
                    .findAny()
                    .orElseThrow(
                            () -> new EntityNotFoundException("Bicycle not found in source hub: " + reservation.getFrom().getName())
                    );

            // Remove the Bicycle from the source HubContent
            reservation.setBicycle(bicycleToMove);
            sourceHubContent.getBicycles().remove(bicycleToMove);
            targetHubContent.getBicycles().add(bicycleToMove);
            reservation.setBicycle(bicycleToMove);
            reservationRepository.save(reservation);
            hubContentRepository.save(sourceHubContent);
            hubContentRepository.save(targetHubContent);

        }
        reservation.setReservationStatus(ReservationStatus.FINISHED);
        reservation.setEndTime(LocalDateTime.now());
        reservationRepository.save(reservation);

//        System.out.println(bicycleToMove.getId());

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


    public ApiResponseClass getMyReservationsByStatus(String status) {
        var user = utilsService.extractCurrentUser();
        if(user == null || !(user instanceof Client)){
            throw new AuthorizationServiceException("User not authorized");
        }
        Integer clientID = user.getId();
        List<Reservation> reservationList = reservationRepository.findByClientIdAndReservationStatus(clientID , ReservationStatus.valueOf(status));

        if(reservationList.isEmpty()) return new ApiResponseClass("No Reservations found", HttpStatus.OK,LocalDateTime.now());

        List<ReservationResponse> responseList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
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
            responseList.add(response);
        }
        return new ApiResponseClass("Get my reservations with state: " + status + " done successfully", HttpStatus.OK, LocalDateTime.now(), responseList);
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


    public ApiResponseClass payForReservation(PaymentRequest request) {
        BaseUser reservationOwner = utilsService.extractCurrentUser();
        if(!(reservationOwner instanceof Client)){
            throw new ApiRequestException("YOU ARE NOT A CLIENT AND NOT SUPPORTED TO RESERVATION");
        }
        Client client = clientRepository.findById(reservationOwner.getId()).orElseThrow(
                ()-> new ApiRequestException("Client not found")
        );
        paymentRequestValidator.validate(request);
        Reservation reservation = reservationRepository.findById(request.getReservationID()).orElseThrow(
                ()-> new ApiRequestException("reservation not found with ID: " + request.getReservationID())
        );

        if(client.getWallet() == null){
            throw new ApiRequestException("YOU DON'T HAVE WALLET, PLEASE CREATE WALLET FIRST");
        }

//        if (isOverlappingCheck((int) reservation.getDuration() , reservation.getStartTime()))
//            throw new ApiRequestException("SORRY, THIS BICYCLE BOOKED BEFORE YOU SUBMIT YOUR BOOKING");

//        if((!Objects.equals(request.getWalletPassword(), client.getWallet().getSecurityCode()))){
//            throw new AuthorizationServiceException("UnAuthorize process, password not correct");
//        }

        if(!passwordEncoder.matches(request.getWalletPassword(), client.getWallet().getSecurityCode())){
            throw new AuthorizationServiceException("UnAuthorize process, password not correct");
        }

        Double reservationPrice = reservation.getBicycle().getModel_price().getPrice() * reservation.getDuration();
        Double walletBalance = client.getWallet().getBalance();

        if(reservation.getPaymentMethod().equals(PaymentMethod.Wallet)){
            if(walletBalance < reservationPrice){
                throw new ApiRequestException("Not enough wallet, please charge your wallet first then try again");
            }
        }
        client.getWallet().setBalance(walletBalance - reservationPrice);
        reservation.setReservationStatus(ReservationStatus.NOT_STARTED);
        walletRepository.save(client.getWallet());
        clientRepository.save(client);
        System.out.println(client.getWallet().getBalance());
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
                .price(reservationPrice)
                .build();
        return new ApiResponseClass("Your reservation has been confirmed successfully", HttpStatus.ACCEPTED, LocalDateTime.now(),response);
    }

}
