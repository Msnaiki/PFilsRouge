package com.tasky.appManager.controllers;

import com.tasky.appManager.dtos.TaskDTO;
import com.tasky.appManager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ... existing code ...

    @PostMapping("/create")
    public ResponseEntity<Object> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            // Perform validation or other business logic as needed
            if (taskDTO == null || taskDTO.getTitle() == null || taskDTO.getDescription() == null) {
                return ResponseEntity.badRequest().body("Task details are required.");
            }

            // Create the task
            TaskDTO createdTaskDTO = taskService.createTask(taskDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions, e.g., Manager not found, Employee not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating task: " + e.getMessage());
        }
    }
    //get all tasks
    @GetMapping("/getAllTasks")
    public ResponseEntity<Object> getAllTasks() {
        try {
            List<TaskDTO> allTasks = taskService.getAllTasks();

            return ResponseEntity.ok(allTasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving tasks: " + e.getMessage());
        }
    }
    // get tasks by manager
    @GetMapping("/em/getByManager")
    public ResponseEntity<Object> getTasksByManager(@RequestParam Long managerId) {
        try {
            List<TaskDTO> tasksByManager = taskService.getTasksByManager(managerId);

            return ResponseEntity.ok(tasksByManager);
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions, e.g., Manager not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving tasks by manager: " + e.getMessage());
        }
    }

    //get tasks by employee
    @GetMapping("/em/getByEmployee")
    public ResponseEntity<Object> getTasksByEmployee(@RequestParam Long employeeId) {
        try {
            List<TaskDTO> tasksByEmployee = taskService.getTasksByEmployee(employeeId);

            return ResponseEntity.ok(tasksByEmployee);
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions, e.g., Employee not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving tasks by employee: " + e.getMessage());
        }
    }
    //update task
    @PutMapping("/updateTask")
    public ResponseEntity<Object> updateTask(@RequestBody TaskDTO updatedTaskDTO) {
        try {
            TaskDTO updatedTask = taskService.updateTask(updatedTaskDTO);

            return ResponseEntity.ok(updatedTask);
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions, e.g., Task not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating task: " + e.getMessage());
        }
    }
    //employee updates the status
    @PutMapping("/em/updateStatus")
    public ResponseEntity<Object> updateTaskStatus(
            @RequestParam Long taskId,
            @RequestParam Long employeeId,
            @RequestParam String newStatus) {

        try {
            TaskDTO updatedTaskDTO = taskService.updateTaskStatus(taskId, employeeId, newStatus);
            return ResponseEntity.ok(updatedTaskDTO);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating task status: " + e.getMessage());
        }
    }
    //remove one task
    @DeleteMapping("/removeTask")
    public ResponseEntity<Object> removeTask(@RequestParam Long taskId) {
        try {
            taskService.removeTask(taskId);
            return ResponseEntity.ok("Task removed successfully");
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions, e.g., Task not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing task: " + e.getMessage());
        }
    }


}