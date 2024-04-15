package com.tasky.appManager.dtos;

import lombok.Data;

@Data
public class TaskDTO {
    private Long taskId;
    private String title;
    private String description;
    private ManagerDto assignedBy;
    private EmployeeDto assignedTo;
    private String status = "Pending";
}
