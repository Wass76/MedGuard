package com.CareemSystem.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByPhoneNumber(String phoneNumber);
//    Optional<User> findByPhone(String phone);
}
