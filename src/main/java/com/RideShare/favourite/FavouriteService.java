package com.RideShare.favourite;

import com.RideShare.Response.ApiResponseClass;
import com.RideShare.object.Response.ClientBicycleResponse;
import com.RideShare.object.Service.BicycleService;
import com.RideShare.utils.Service.UtilsService;
import com.RideShare.utils.Validator.ObjectsValidator;
import com.RideShare.utils.exception.ApiRequestException;
import com.RideShare.object.Model.Bicycle;
import com.RideShare.object.Repository.BicycleRepository;
import com.RideShare.user.Model.Client;
import com.RideShare.user.Repository.ClientRepository;
import com.RideShare.user.Response.ClientResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequestMapping
public class FavouriteService {
    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    private ObjectsValidator<FavouriteRequest> favouriteValidator;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BicycleRepository bicycleRepository;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private BicycleService bicycleService;

    public FavouriteResponse extractClientResponse(Favourite favourite){

        ClientBicycleResponse bicycleResponse = bicycleService.extractToClientResponse(favourite.getBicycle());

        ClientResponse clientResponse = ClientResponse.builder()
                .firstName(favourite.getClient().getFirstName())
                .lastName(favourite.getClient().getLastName())
                .username(favourite.getClient().get_username())
                .phoneNumber(favourite.getClient().getPhone())
                .id(favourite.getClient().getId())
                .birthDate(favourite.getClient().getBirthday())
                .build();

        return FavouriteResponse
                .builder()
                .client(clientResponse)
                .bicycle(bicycleResponse)
                .id(favourite.getId())
                .build();
    }

    public ApiResponseClass getFavouritesById(Integer id) {

        Favourite favourite = favouriteRepository.findById(id).orElseThrow(
                ()-> new ApiRequestException("Favourite not found with id: " + id)
        );
        FavouriteResponse response = extractClientResponse(favourite);
        return new ApiResponseClass("Get favourite done successfully" , HttpStatus.OK , LocalDateTime.now() , response);
    }

    public ApiResponseClass getFavouritesByClient() {
        var user = utilsService.extractCurrentUser();
        if(!(user instanceof Client)){
            throw new ApiRequestException("this user is not a client");
        }
        Integer clientId = (user.getId());

       List <Favourite> favouriteList = favouriteRepository.findFavouriteByClientId(clientId);
       if (favouriteList.isEmpty()) {
           throw new ApiRequestException("There is no Favourite found for clientId: " + clientId);
       }
       List <FavouriteResponse> favouriteResponseList = new ArrayList<>();
       for (Favourite favourite : favouriteList) {
           favouriteResponseList.add(
                   extractClientResponse(favourite)
           );
       }
       return new ApiResponseClass("Get favourite items For client successfully" , HttpStatus.OK , LocalDateTime.now() , favouriteResponseList);
    }

    public ApiResponseClass getFavouritesByBicycle(Integer bicycleId) {

        List<Favourite> favouriteList = favouriteRepository.findFavouriteByBicycleId(bicycleId);
        if (favouriteList.isEmpty()) {
            throw new ApiRequestException("There is no Favourite found for bicycleId: " + bicycleId);
        }
        List <FavouriteResponse> favouriteResponseList = new ArrayList<>();
        for (Favourite favourite : favouriteList) {
            favouriteResponseList.add(extractClientResponse(favourite));
        }
        return new ApiResponseClass("Get favourite items by bicycle id done successfully" , HttpStatus.OK , LocalDateTime.now() , favouriteResponseList);
    }

    @Transactional
    public ApiResponseClass AddNewFavourite(FavouriteRequest request) {

        favouriteValidator.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getName());

        Client client = clientRepository.findByPhone(authentication.getName()).orElseThrow(
                () -> new ApiRequestException("Client not found with name: " + authentication.getName())
        );
        Bicycle bicycle = bicycleRepository.findById(request.getBicycleId()).orElseThrow(
                ()-> new ApiRequestException("there is no bicycle with id: " + request.getBicycleId())
        );

        Favourite favourite = Favourite.builder()
                .client(client)
                .bicycle(bicycle)
                .build();
        favouriteRepository.save(favourite);

        FavouriteResponse response = extractClientResponse(favourite);

        return new ApiResponseClass("Add new favourite successfully" , HttpStatus.CREATED , LocalDateTime.now() , response);
    }

    public ApiResponseClass DeleteFavourite(Integer id) {
        Favourite favourite = favouriteRepository.findById(id).orElseThrow(
                () -> new ApiRequestException("Favourite not found with id: " + id)
        );
        favouriteRepository.delete(favourite);
        return new ApiResponseClass("Delete favourite successfully" , HttpStatus.OK , LocalDateTime.now());
    }
}
