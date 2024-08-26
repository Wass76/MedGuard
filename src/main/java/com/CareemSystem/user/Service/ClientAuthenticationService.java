package com.CareemSystem.user.Service;

import com.CareemSystem.utils.Validator.ObjectsValidator;
import com.CareemSystem.user.Request.AuthenticationRequest;
import com.CareemSystem.user.Response.AuthenticationResponse;
import com.CareemSystem.user.Request.ClientRegisterRequest;
import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.config.JwtService;
import com.CareemSystem.utils.exception.ApiRequestException;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ClientAuthenticationService {


    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ObjectsValidator<ClientRegisterRequest> registerClientValidator;
    private final ObjectsValidator<AuthenticationRequest> authenticationRequestValidator;

    public ApiResponseClass register(ClientRegisterRequest request) {
        registerClientValidator.validate(request);

        Optional<Client> username_found = clientRepository.findBy_username(request.getUsername());
        if (username_found.isPresent()) {
            throw new ApiRequestException("username already in use");
        }
        Optional<Client> phone_found = clientRepository.findByPhone(request.getPhone());
        if (phone_found.isPresent()) {
            throw new ApiRequestException("phone number already in use");
        }

        var client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                ._username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthday(request.getBirthDate())
                .active(true)
                .build();

        clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(client.getId())
                .phone(client.getPhone())
                ._username(client.get_username())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .token(jwtToken)
                .build();
        return new ApiResponseClass("Create new account done successfully", HttpStatus.CREATED , LocalDateTime.now(),response);

    }

    public ApiResponseClass authenticate(AuthenticationRequest request) {
        authenticationRequestValidator.validate(request);
//        System.out.println("Waaa");
        Optional<Client> user = clientRepository.findByPhone(request.getPhone());
        if(!user.isPresent()) {
            throw new ApiRequestException("user not found");
        }
        var jwtToken = jwtService.generateToken(user.get());
        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(user.get().getId())
                .phone(user.get().getPhone())
                ._username(user.get().get_username())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .token(jwtToken)
                .build();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhone()
                            ,request.getPassword()
                    )
            );
        }
        catch (AuthenticationException e) {
            throw new ApiRequestException("invalid email or password");
        }
        return new ApiResponseClass("Login successful", HttpStatus.OK , LocalDateTime.now(),response);
//      Optional<Client> user1 = clientRepository.findBy_username(request.getPhone());
//      if(user1.isPresent()) {
//          var jwtToken = jwtService.generateToken(user1.get());
//          AuthenticationResponse response = AuthenticationResponse.builder()
//                  .token(jwtToken)
//                  .build();
//          return new ApiResponseClass("Login successful", HttpStatus.OK , LocalDateTime.now(),response);
//      }
    }





}
