package com.CareemSystem.user.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private LocalDate birthDate;

}
