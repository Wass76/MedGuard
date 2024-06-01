package com.CareemSystem.user.Service;

import com.CareemSystem.user.Request.AuthenticationRequest;
import com.CareemSystem.user.Response.AuthenticationResponse;
import com.CareemSystem.user.Request.ClientRegisterRequest;
import com.CareemSystem.Response.ApiResponseClass;
import com.CareemSystem.config.JwtService;
import com.CareemSystem.exception.ApiRequestException;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public ApiResponseClass register(ClientRegisterRequest request) {
        var client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthday(request.getBirthDate())
                .active(true)
                .build();

        clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        return
                new ApiResponseClass("Create new account done successfully", HttpStatus.CREATED , LocalDateTime.now(),response);

    }

    public ApiResponseClass authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername()
                    ,request.getPassword()
            )
        );
        System.out.println("Waaa");
      Optional<Client> user = clientRepository.findByUsername(request.getUsername());
      if(user.isPresent()) {
          var jwtToken = jwtService.generateToken(user.get());
          AuthenticationResponse response = AuthenticationResponse.builder()
                  .token(jwtToken)
                  .build();
          return new ApiResponseClass("Login successful", HttpStatus.OK , LocalDateTime.now(),response);
      }
      Optional<Client> user1 = clientRepository.findByUsername(request.getUsername());
      if(user1.isPresent()) {
          var jwtToken = jwtService.generateToken(user1.get());
          AuthenticationResponse response = AuthenticationResponse.builder()
                  .token(jwtToken)
                  .build();
          return new ApiResponseClass("Login successful", HttpStatus.OK , LocalDateTime.now(),response);
      }
    throw new ApiRequestException("Invalid phone number");
    }





}
