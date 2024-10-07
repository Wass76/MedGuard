package com.RideShare.user.Repository;

import com.RideShare.user.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByPhone(String phone);
}
