package com.tasky.appManager.reposetories;

import com.tasky.appManager.domaine.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface BlacklistedTokenRepo extends JpaRepository<BlacklistedToken, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM BlacklistedToken b WHERE b.expirationDate <= :now")
    void deleteExpiredTokens(Date now);
}
