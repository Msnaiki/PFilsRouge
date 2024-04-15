package com.tasky.appManager.services;

import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.domaine.Manager;
import com.tasky.appManager.domaine.Task;
import com.tasky.appManager.dtos.TaskDTO;
import com.tasky.appManager.reposetories.EmployeeRepo;
import com.tasky.appManager.reposetories.ManagerRepo;
import com.tasky.appManager.reposetories.TaskRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepo taskRepository;
    private final ModelMapper modelMapper;
    private final ManagerRepo managerRepository;
    private final EmployeeRepo employeeRepository;

    @Autowired
    public TaskService(TaskRepo taskRepository, ModelMapper modelMapper,
                       ManagerRepo managerRepository, EmployeeRepo employeeRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.managerRepository = managerRepository;
        this.employeeRepository = employeeRepository;
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        // Convert TaskDTO to Task entity
        Task convertedTask = modelMapper.map(taskDTO, Task.class);

        // Fetch and set Manager if assignedBy is provided
        if (taskDTO.getAssignedBy() != null && taskDTO.getAssignedBy().getManagerId() != null) {
            Manager fetchedManager = managerRepository.findById(taskDTO.getAssignedBy().getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + taskDTO.getAssignedBy().getManagerId()));

            convertedTask.setAssignedBy(fetchedManager);
        }

        // Fetch and set Employee if assignedTo is provided
        if (taskDTO.getAssignedTo() != null && taskDTO.getAssignedTo().getEmployeeId() != null) {
            Employee fetchedEmployee = employeeRepository.findById(taskDTO.getAssignedTo().getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + taskDTO.getAssignedTo().getEmployeeId()));

            convertedTask.setAssignedTo(fetchedEmployee);
        }

        // Set default status if not provided
        if (convertedTask.getStatus() == null) {
            convertedTask.setStatus("Pending");
        }

        // Save the task
        Task createdTask = taskRepository.save(convertedTask);

        // Convert the created Task to TaskDTO for response
        return convertToDto(createdTask);
    }

    // fetch all tasks
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //fetch by manager
    public List<TaskDTO> getTasksByManager(Long managerId) {
        // Check if the manager exists
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + managerId));

        // Retrieve tasks assigned by the manager
        List<Task> tasks = taskRepository.findByAssignedBy(manager);

        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //get tasks by employee

    public List<TaskDTO> getTasksByEmployee(Long employeeId) {
        // Check if the employee exists
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        // Retrieve tasks assigned to the employee
        List<Task> tasks = taskRepository.findByAssignedTo(employee);

        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    //update task
    public TaskDTO updateTask(TaskDTO updatedTaskDTO) {
        // Check if the task exists
        Task existingTask = taskRepository.findById(updatedTaskDTO.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + updatedTaskDTO.getTaskId()));

        // Update properties of the existing task
        existingTask.setTitle(updatedTaskDTO.getTitle());
        existingTask.setDescription(updatedTaskDTO.getDescription());
        existingTask.setStatus(updatedTaskDTO.getStatus());

        // Update assignedTo if provided
        if (updatedTaskDTO.getAssignedTo() != null && updatedTaskDTO.getAssignedTo().getEmployeeId() != null) {
            Employee assignedTo = employeeRepository.findById(updatedTaskDTO.getAssignedTo().getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + updatedTaskDTO.getAssignedTo().getEmployeeId()));
            existingTask.setAssignedTo(assignedTo);
        }

        // Save the updated task
        Task updatedTask = taskRepository.save(existingTask);

        // Convert the updated Task to TaskDTO for response
        return convertToDto(updatedTask);
    }
        //remove Task
        public void removeTask(Long taskId) {
            // Check if the task exists
            Task existingTask = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

            // Remove the task
            taskRepository.delete(existingTask);
        }
    //employee update task status
    public TaskDTO updateTaskStatus(Long taskId, Long employeeId, String newStatus) throws AccessDeniedException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        if (!isAssignedToEmployee(task, employeeId)) {
            throw new AccessDeniedException("Employee not authorized to update this task.");
        }

        task.setStatus(newStatus);
        Task updatedTask = taskRepository.save(task);
        return convertToDto(updatedTask);
    }

    //check if it's the right employee
    private boolean isAssignedToEmployee(Task task, Long employeeId) {
        return task.getAssignedTo() != null && task.getAssignedTo().getEmployeeId().equals(employeeId);
    }
    // Conversion methods
    private TaskDTO convertToDto(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

}