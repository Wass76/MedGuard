package com.CareemSystem.auth;


import com.CareemSystem.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String password;
    private Role role;
}
