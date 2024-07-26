package com.CareemSystem.user.Request;


import com.CareemSystem.utils.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegisterRequest {

    @NotBlank(message = "firstName can't be null")
    private String firstName;

    @NotBlank(message = "lastName can't be null")
    private String lastName;

    @NotBlank(message = "phone number can't be null")
    @Size(message = "phone number must be absolutely 10" , min = 10, max = 10)
    private String phone;

    @NotBlank(message = "username can't be null")
    private String username;

    @NotNull(message = "birthDate can't be null")
    private LocalDate birthDate;

    @NotBlank(message = "password can't be null")
    @ValidPassword(message = "Invalid password")
    private String password;

    @NotBlank(message = "password can't be null")
    private String ConfirmPassword;
//    private Role role;
}
