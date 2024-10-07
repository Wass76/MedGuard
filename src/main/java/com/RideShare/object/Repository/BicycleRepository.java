package com.RideShare.object.Repository;

import com.RideShare.object.Enum.BicycleCategory;
import com.RideShare.object.Model.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BicycleRepository extends JpaRepository<Bicycle,Integer> {

    List<Bicycle> findBicycleByType(BicycleCategory category);

    List<Bicycle> findBicyclesByHasOfferTrue();

}
