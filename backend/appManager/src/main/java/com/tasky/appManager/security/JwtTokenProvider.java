package com.tasky.appManager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tasky.appManager.domaine.Admin;
import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.domaine.Manager;
import com.tasky.appManager.domaine.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private long jwtExpirationInMs;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        // Determine the user's role
        String role = getUserRole(user);

        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role",role)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC512(jwtSecret));
    }

    private String getUserRole(User user) {
        if (user instanceof Admin) {
            return "ROLE_ADMIN";
        } else if (user instanceof Manager) {
            return "ROLE_MANAGER";
        } else if (user instanceof Employee) {
            return "ROLE_EMPLOYEE";
        }
        return "ROLE_GUEST"; // Default role or unknown role
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(jwtSecret)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsernameFromJWT(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public List<String> getAuthoritiesFromJWT(String token) {
        DecodedJWT jwt = JWT.decode(token);
        String role = jwt.getClaim("role").asString();
        return Collections.singletonList(role);
    }
}