package com.CareemSystem.hub.Repository;

import com.CareemSystem.hub.Entity.HubContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HubContentRepository  extends JpaRepository<HubContent, Integer> {

    public Optional<HubContent> findByHubId(Integer hubId);
}
