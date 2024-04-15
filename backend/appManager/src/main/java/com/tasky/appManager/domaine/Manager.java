package com.tasky.appManager.domaine;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity @Data @RequiredArgsConstructor

public class Manager extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ManagerId;
    @JsonManagedReference
    @OneToMany(mappedBy = "assignedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasksCreated;

    @JsonManagedReference
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;

    public String getRole() {
        return "manager";
    }

}