package com.RideShare.user.Repository;

import com.RideShare.user.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {

    Optional<Client> findByPhone(String phoneNumber);
    Optional<Client> findBy_username(String phone);
}
