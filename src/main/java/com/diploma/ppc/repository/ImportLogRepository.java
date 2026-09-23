package com.diploma.ppc.repository;

import com.diploma.ppc.entity.ImportLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportLogRepository extends JpaRepository<ImportLog, Long> {
    List<ImportLog> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
