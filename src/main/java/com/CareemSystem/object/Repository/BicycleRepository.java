package com.CareemSystem.object.Repository;

import com.CareemSystem.object.Enum.BicycleCategory;
import com.CareemSystem.object.Model.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BicycleRepository extends JpaRepository<Bicycle,Integer> {

    List<Bicycle> findBicycleByType(BicycleCategory category);

    List<Bicycle> findBicyclesByDiscountPriceTrue();

}
