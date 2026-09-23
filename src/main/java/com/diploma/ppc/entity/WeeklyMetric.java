package com.diploma.ppc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_metrics", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"campaign_id", "week_start_date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(name = "week_start_date", nullable = false)
    private LocalDate weekStartDate;

    @Builder.Default
    private Long impressions = 0L;

    @Builder.Default
    private Long clicks = 0L;

    @Builder.Default
    private Long conversions = 0L;

    @Builder.Default
    private BigDecimal spend = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal revenue = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Производные метрики - считаются "на лету", не хранятся в БД
    @Transient
    public double getCtr() {
        return impressions == 0 ? 0 : (double) clicks / impressions * 100;
    }

    @Transient
    public BigDecimal getCpc() {
        return clicks == 0 ? BigDecimal.ZERO : spend.divide(BigDecimal.valueOf(clicks), 2, java.math.RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getCpa() {
        return conversions == 0 ? BigDecimal.ZERO : spend.divide(BigDecimal.valueOf(conversions), 2, java.math.RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getRoas() {
        return spend.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : revenue.divide(spend, 2, java.math.RoundingMode.HALF_UP);
    }
}
