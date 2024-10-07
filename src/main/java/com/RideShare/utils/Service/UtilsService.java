package com.RideShare.utils.Service;

import com.RideShare.user.Model.BaseUser;
import com.RideShare.user.Repository.AdminRepository;
import com.RideShare.user.Repository.ClientRepository;
import com.RideShare.utils.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilsService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdminRepository adminRepository;


    public BaseUser extractCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UnAuthorizedException("Authentication error");
        }
        var client = clientRepository.findByPhone(authentication.getName()).orElse(null);
        if(client == null){
            return adminRepository.findByPhone(authentication.getName()).orElse(null);
        }
        return client;
    }
}
