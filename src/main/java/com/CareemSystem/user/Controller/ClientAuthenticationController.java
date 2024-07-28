package com.CareemSystem.user.Controller;

import com.CareemSystem.user.Request.AuthenticationRequest;
import com.CareemSystem.user.Request.ClientRegisterRequest;
import com.CareemSystem.user.Service.ClientAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Client Auth")
public class ClientAuthenticationController {

    private final ClientAuthenticationService clientAuthenticationService;

    @PostMapping("/register")
    @Operation(
            description = "This endpoint build to Register new account in our system",
            summary = "Create account",
            responses = {
                    @ApiResponse(
                            description ="Create account done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> register(
            @RequestBody ClientRegisterRequest request
    ){
       return ResponseEntity.ok(clientAuthenticationService.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(
            description = "This endpoint build to Login to your account in our system",
            summary = "Login to our system",
            responses = {
                    @ApiResponse(
                            description = "Login account done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
       return ResponseEntity.ok(clientAuthenticationService.authenticate(request));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<AuthenticationResponse> logout(
//            @RequestBody AuthenticationRequest request
//    ){
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }
}
