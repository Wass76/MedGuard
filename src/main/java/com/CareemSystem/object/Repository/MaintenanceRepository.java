package com.CareemSystem.object.Repository;

import com.CareemSystem.object.Model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    public List<Maintenance> findByBicycleId(Integer bicycleId);
}
