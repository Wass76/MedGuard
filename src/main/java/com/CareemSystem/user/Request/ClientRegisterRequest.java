package com.CareemSystem.user.Request;


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

    private String firstName;

    private String lastName;

    private String phone;
    private String username;
    private LocalDate birthDate;

    private String password;
    private String ConfirmPassword;
//    private Role role;
}
