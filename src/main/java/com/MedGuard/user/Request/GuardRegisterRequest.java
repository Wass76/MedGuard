package com.MedGuard.user.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardRegisterRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private LocalDate birthDate;
}
