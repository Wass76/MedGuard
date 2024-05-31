package com.CareemSystem.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Account Management")
public class UserController {

    private final UserService userService;

    @Operation(
            description = "This endpoint build for make user able to change his password by enter old and new password",
            summary =  "Change password of user",
            responses ={
                    @ApiResponse(
                        description = "Change password done successfully",
                        responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Wrong old password / Passwords are not the same",
                            responseCode = "403"
                    )
            }
    )
    @PutMapping("/change-password")
//    @PreAuthorize()
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    )
    {
        userService.changePassword(request , connectedUser);
        return ResponseEntity.accepted().body("Change password done successfully");
    }
}
