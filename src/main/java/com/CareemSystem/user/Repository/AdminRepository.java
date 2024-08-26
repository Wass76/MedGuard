package com.CareemSystem.user.Repository;

import com.CareemSystem.user.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByPhone(String phone);
}
