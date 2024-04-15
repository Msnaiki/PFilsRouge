package com.tasky.appManager.services;

import com.tasky.appManager.domaine.BlacklistedToken;
import com.tasky.appManager.reposetories.BlacklistedTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenStore {
    @Autowired
    private BlacklistedTokenRepo blacklistedTokenRepository;

    public void invalidateToken(String token, long ttl) {
        Date expirationDate = new Date(System.currentTimeMillis() + ttl);
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expirationDate);
        blacklistedTokenRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokenRepository.findById(token)
                .map(blacklistedToken -> blacklistedToken.getExpirationDate().after(new Date()))
                .orElse(false);
    }
}
