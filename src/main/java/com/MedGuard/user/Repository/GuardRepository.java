package com.MedGuard.user.Repository;

import com.MedGuard.user.Model.Guard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardRepository extends JpaRepository<Guard, Integer> {

    Optional<Guard> findByPhone(String phone);
    Optional<Guard> findByEmail(String email);
}
