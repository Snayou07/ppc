package com.diploma.ppc.repository;

import com.diploma.ppc.entity.WeeklyMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyMetricRepository extends JpaRepository<WeeklyMetric, Long> {

    List<WeeklyMetric> findByCampaignId(Long campaignId);

    Optional<WeeklyMetric> findByCampaignIdAndWeekStartDate(Long campaignId, LocalDate weekStartDate);

    @Query("SELECT wm FROM WeeklyMetric wm WHERE wm.campaign.id = :campaignId " +
           "AND wm.weekStartDate BETWEEN :from AND :to ORDER BY wm.weekStartDate")
    List<WeeklyMetric> findByCampaignAndPeriod(@Param("campaignId") Long campaignId,
                                                @Param("from") LocalDate from,
                                                @Param("to") LocalDate to);

    @Query("SELECT wm FROM WeeklyMetric wm WHERE wm.campaign.account.id = :accountId " +
           "AND wm.weekStartDate BETWEEN :from AND :to ORDER BY wm.weekStartDate")
    List<WeeklyMetric> findByAccountAndPeriod(@Param("accountId") Long accountId,
                                               @Param("from") LocalDate from,
                                               @Param("to") LocalDate to);
}
