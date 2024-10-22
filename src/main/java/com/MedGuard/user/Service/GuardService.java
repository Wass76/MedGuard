package com.MedGuard.user.Service;

import com.MedGuard.Response.ApiResponseClass;
import com.MedGuard.config.JwtService;
import com.MedGuard.user.Model.Guard;
import com.MedGuard.user.Repository.GuardRepository;
import com.MedGuard.user.Request.AuthenticationRequest;
import com.MedGuard.user.Request.GuardRegisterRequest;
import com.MedGuard.user.Response.AuthenticationResponse;
import com.MedGuard.utils.Validator.ObjectsValidator;
import com.MedGuard.utils.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GuardService {

    @Autowired
    private GuardRepository guardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    ObjectsValidator<GuardRegisterRequest> guardRegisterRequestObjectsValidator;

    public ApiResponseClass register(GuardRegisterRequest request) {
        guardRegisterRequestObjectsValidator.validate(request);

        Optional<Guard> username_found = guardRepository.findByEmail(request.getEmail());
        if (username_found.isPresent()) {
            throw new ApiRequestException("username already in use");
        }
        Optional<Guard> phone_found = guardRepository.findByPhone(request.getPhone());
        if (phone_found.isPresent()) {
            throw new ApiRequestException("phone number already in use");
        }
        Guard guard = Guard.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        guardRepository.save(guard);

        var jwtToken = jwtService.generateToken(guard);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(guard.getId())
                .phone(guard.getPhone())
                .email(guard.getEmail())
                .firstName(guard.getFirstName())
                .lastName(guard.getLastName())
                .token(jwtToken)
                .build();
        return new ApiResponseClass("Create new account done successfully", HttpStatus.CREATED, LocalDateTime.now(), response);
    }

    public ApiResponseClass authenticate(AuthenticationRequest request) {

        Optional<Guard> user = guardRepository.findByPhone(request.getPhone());
        if(!user.isPresent()) {
            throw new ApiRequestException("user not found");
        }
        var jwtToken = jwtService.generateToken(user.get());
        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(user.get().getId())
                .phone(user.get().getPhone())
                .email(user.get().getEmail())
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
    }
}
