package com.tasky.appManager.domaine;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@MappedSuperclass
public class User {
    @Column(unique = true)
    private String username;
    private String name;
    private String password;
}