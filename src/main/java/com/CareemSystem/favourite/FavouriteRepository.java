package com.CareemSystem.favourite;

import com.CareemSystem.user.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {


    List<Favourite> findFavouriteByClientId(Integer clientId);

    List<Favourite> findFavouriteByBicycleId(Integer bicycleId);

//    Optional<Favourite> findFavouriteBy(Integer id);
}
