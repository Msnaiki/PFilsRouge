package com.tasky.appManager.security;

import com.tasky.appManager.reposetories.BlacklistedTokenRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Component
public class TokenCleanupTask {

    @Autowired
    private BlacklistedTokenRepo blacklistedTokenRepository;

    @Scheduled(cron = "0 0 1 * * ?") // This cron expression schedules the task to run at 1 AM daily
    public void cleanExpiredTokens() {
        blacklistedTokenRepository.deleteExpiredTokens(new Date());
    }
}