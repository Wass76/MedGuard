package com.CareemSystem.user;

import com.CareemSystem.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void changePassword(
            ChangePasswordRequest request, Principal connectedUser){

        var user  = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

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
        userRepository.save(user);

    }

}
