package com.CareemSystem.object.Repository;

import com.CareemSystem.object.Model.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<Bicycle,Integer> {

}
