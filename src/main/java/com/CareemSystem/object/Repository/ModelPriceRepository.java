package com.CareemSystem.object.Repository;

import com.CareemSystem.object.Model.ModelPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModelPriceRepository extends JpaRepository<ModelPrice, Integer> {

    public Optional<ModelPrice> findByModel(String modelID);
}
