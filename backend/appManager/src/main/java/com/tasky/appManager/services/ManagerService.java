package com.tasky.appManager.services;

import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.domaine.Manager;
import com.tasky.appManager.domaine.Task;
import com.tasky.appManager.dtos.EmployeeDto;
import com.tasky.appManager.dtos.ManagerDto;
import com.tasky.appManager.dtos.TaskDTO;
import com.tasky.appManager.reposetories.ManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private final PasswordEncoder passwordEncoder;
    private final ManagerRepo managerRepository;


    @Autowired
    public ManagerService( PasswordEncoder passwordEncoder,ManagerRepo managerRepository) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create manager
    public Manager createManager(Manager manager) {
        try {
            // Encode password
            manager.setPassword(passwordEncoder.encode(manager.getPassword()));
            return managerRepository.save(manager);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username is already taken. Please choose a different username.");
        }
    }

    // Get all managers
    public List<ManagerDto> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Find by username
    public Optional<Manager> findManagerByUsername(String username) {
        return managerRepository.findByUsername(username);
    }

    // Update manager
    public ManagerDto updateManager(Manager manager) {
        Optional<Manager> optionalManager = managerRepository.findById(manager.getManagerId());
        if (optionalManager.isPresent()) {
            Manager existingManager = optionalManager.get();
            existingManager.setUsername(manager.getUsername());
            existingManager.setName(manager.getName());
            existingManager.setPassword(passwordEncoder.encode(manager.getPassword()));
            Manager updatedManager = managerRepository.save(existingManager);
            return convertToDto(updatedManager);
        } else {
            throw new IllegalArgumentException("Manager not found with ID: " + manager.getManagerId());
        }
    }

    // Delete manager
    public void deleteManager(Long managerId) {
        if (!managerRepository.existsById(managerId)) {
            throw new IllegalArgumentException("Manager not found with ID: " + managerId);
        }
        managerRepository.deleteById(managerId);
    }

    private ManagerDto convertToDto(Manager manager) {
        ManagerDto dto = new ManagerDto();
        dto.setManagerId(manager.getManagerId());
        dto.setUsername(manager.getUsername());
        dto.setName(manager.getName());
        return dto;
    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setUsername(employee.getUsername());
        dto.setName(employee.getName());
        dto.setBestQuality(employee.getBestQuality());
        if (employee.getManager() != null) {
            dto.setManagerDto(convertToDto(employee.getManager()));
        }
        return dto;
    }

    private TaskDTO convertToDto(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        if (task.getAssignedBy() != null) {
            dto.setAssignedBy(convertToDto(task.getAssignedBy()));
        }
        if (task.getAssignedTo() != null) {
            dto.setAssignedTo(convertToDto(task.getAssignedTo()));
        }
        return dto;
    }
    public Manager convertToEntity(ManagerDto managerDto) {
        Manager manager = new Manager();
        manager.setManagerId(managerDto.getManagerId());
        manager.setUsername(managerDto.getUsername());
        manager.setName(managerDto.getName());
        return manager;
    }
    public ManagerDto convertToDto(Optional<Manager> manager) {
        ManagerDto dto = new ManagerDto();
        dto.setManagerId(manager.get().getManagerId());
        dto.setUsername(manager.get().getUsername());
        dto.setName(manager.get().getName());
        return dto;
    }
    public Optional<ManagerDto> updateManagerPassword(String username, String newPassword) {
        Optional<Manager> optionalManager = managerRepository.findByUsername(username);
        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            manager.setPassword(passwordEncoder.encode(newPassword));
            Manager updatedManager = managerRepository.save(manager);
            return Optional.of(convertToDto(updatedManager));
        } else {
            throw new IllegalArgumentException("Manager not found with username: " + username);
        }
    }

}
