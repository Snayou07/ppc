package com.diploma.ppc.repository;

import com.diploma.ppc.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOwnerId(Long ownerId);
    List<Account> findByPlatformId(Long platformId);
}
