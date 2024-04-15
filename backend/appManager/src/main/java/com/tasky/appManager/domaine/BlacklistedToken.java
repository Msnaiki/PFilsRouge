package com.tasky.appManager.domaine;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity @Data @NoArgsConstructor
public class BlacklistedToken {
    @Id
    private String token;

    private Date expirationDate;


    public BlacklistedToken(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }
}
