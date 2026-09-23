package com.diploma.ppc.repository;

import com.diploma.ppc.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByAccountId(Long accountId);
    List<Campaign> findByPlatformId(Long platformId);
    List<Campaign> findByStatus(Campaign.Status status);
    List<Campaign> findByAccountIdAndStatus(Long accountId, Campaign.Status status);
}
