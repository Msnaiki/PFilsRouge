package com.tasky.appManager.domaine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    private Manager assignedBy;

   @ManyToOne
   @JoinColumn(name = "assigned_to_id")
    private Employee assignedTo;

    private String status = "Pending";


}