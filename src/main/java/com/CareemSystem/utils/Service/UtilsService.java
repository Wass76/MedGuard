package com.CareemSystem.utils.Service;

import com.CareemSystem.user.Model.BaseUser;
import com.CareemSystem.user.Repository.AdminRepository;
import com.CareemSystem.user.Repository.ClientRepository;
import com.CareemSystem.utils.exception.UnAuthorizedException;
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
