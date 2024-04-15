package com.tasky.appManager.domaine;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Entity @Data @NoArgsConstructor

public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AdminId;
    public String getRole() {
        return "admin";
    }
}