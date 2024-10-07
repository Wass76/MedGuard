package com.RideShare.hub.Repository;

import com.RideShare.hub.Entity.HubContent;
import com.RideShare.object.Enum.BicycleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HubContentRepository  extends JpaRepository<HubContent, Integer> {

    public  Optional<HubContent> findByHubId(Integer hubId);

    @Query("SELECT c FROM HubContent c JOIN FETCH c.bicycles b WHERE c.hub.id = :hubId AND b.type = :category")
   Optional<HubContent> findContentByBicycleTypeAndHubId(@Param("hubId") Integer hubId, @Param("category") BicycleCategory category);
//


//    @Query("SELECT c FROM HubContentBicycle c WHERE c.id.hubContentId = :hubID AND c.id.bicyclesId = :bicycleId")
//    Optional<HubContent> findByBicycleIdAndHubId(Integer bicycleId, Integer hubId);
}
