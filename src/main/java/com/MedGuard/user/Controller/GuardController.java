package com.MedGuard.user.Controller;

import com.MedGuard.user.Request.AuthenticationRequest;
import com.MedGuard.user.Request.ChangePasswordRequest;
import com.MedGuard.user.Request.GuardRegisterRequest;
import com.MedGuard.user.Service.GuardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Client Auth")
public class GuardController {

    @Autowired
    private GuardService clientAuthenticationService;

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
            @RequestBody GuardRegisterRequest request
    ){
       return ResponseEntity.ok(clientAuthenticationService.register(request));
    }

    @PostMapping("/login")
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



//    @Operation(
//            description = "This endpoint build for make user able to change his password by enter old and new password",
//            summary =  "Change password of user",
//            responses ={
//                    @ApiResponse(
//                            description = "Change password done successfully",
//                            responseCode = "200"
//                    ),
//                    @ApiResponse(
//                            description = "Wrong old password / Passwords are not the same",
//                            responseCode = "403"
//                    )
//            }
//    )
//    @PutMapping("/change-password")
////    @PreAuthorize()
//    public ResponseEntity<?> changePassword(
//            @RequestBody ChangePasswordRequest request,
//            Principal connectedUser
//    )
//    {
//        baseUserService.changePassword(request , connectedUser);
//        return ResponseEntity.accepted().body("Change password done successfully");
//    }
//    @PostMapping("/logout")
//    public ResponseEntity<AuthenticationResponse> logout(
//            @RequestBody AuthenticationRequest request
//    ){
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }
}
