package com.CareemSystem.user.Controller;

import com.CareemSystem.user.Request.AuthenticationRequest;
import com.CareemSystem.user.Request.ClientRegisterRequest;
import com.CareemSystem.user.Service.ClientAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class ClientAuthenticationController {

    private final ClientAuthenticationService clientAuthenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody ClientRegisterRequest request
    ){
       return ResponseEntity.ok(clientAuthenticationService.register(request));
    }

    @PostMapping("/authenticate")
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
