package com.CareemSystem.user.Request;

import com.CareemSystem.utils.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "phone number can't be null")
    @Size(message = "phone number must be absolutely 10" , min = 10, max = 10)
    private String phone;

    @NotBlank(message = "password can't be null")
    @ValidPassword(message = "Invalid password")
    private String password;
}
