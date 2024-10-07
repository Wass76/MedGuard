package com.RideShare.object.Repository;

import com.RideShare.object.Model.ModelPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelPriceRepository extends JpaRepository<ModelPrice, Integer> {

    public Optional<ModelPrice> findByModel(String modelID);
}
