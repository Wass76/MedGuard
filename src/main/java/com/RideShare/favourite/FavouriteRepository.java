package com.RideShare.favourite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {


    List<Favourite> findFavouriteByClientId(Integer clientId);

    List<Favourite> findFavouriteByBicycleId(Integer bicycleId);

   Optional<Favourite> findByClientIdAndBicycleId(Integer clientId, Integer bicycleId);

//    Optional<Favourite> findFavouriteBy(Integer id);
}
