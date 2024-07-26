package com.CareemSystem.user.Service;

import com.CareemSystem.utils.exception.ApiRequestException;
import com.CareemSystem.user.Request.ChangePasswordRequest;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor

public class BaseUserService {

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    public void changePassword(
            ChangePasswordRequest request, Principal connectedUser){

        var user  = (Client) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        //check if currentPassword is correct
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new ApiRequestException("Wrong password");
//          throw new IllegalStateException("Wrong password");
        }
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new ApiRequestException("Password are not the same");
//            throw new IllegalStateException("Password are not the same");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        clientRepository.save(user);

    }

}
