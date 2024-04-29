package com.tasky.appManager.services;

import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.domaine.Manager;
import com.tasky.appManager.dtos.EmployeeDto;
import com.tasky.appManager.dtos.ManagerDto;
import com.tasky.appManager.reposetories.EmployeeRepo;
import com.tasky.appManager.reposetories.ManagerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepository;
    private final ManagerRepo managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(ManagerRepo managerRepository, EmployeeRepo employeeRepository, PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee createEmployee(Employee employee) {
        // Check if the manager exists
        Long managerId = employee.getManager().getManagerId();
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + managerId));

        // Encrypt password (if the Employee entity has a password field)
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        try {
            employee.setManager(manager);
            return employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Username already exist");
        }
    }

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtos.add(convertToDto(employee));
        }
        return employeeDtos;
    }

    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));
        return convertToDto(employee);
    }

    public Optional<EmployeeDto> updateEmployeeBestQuality(EmployeeDto employeeDto) {
        Long employeeId = employeeDto.getEmployeeId();
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setBestQuality(employeeDto.getBestQuality());

        try {
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return Optional.of(convertToDto(updatedEmployee));
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee", e);
        }
    }

    public void deleteEmployeeById(Long employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }
    }

    private EmployeeDto convertToDto(Employee employee) {
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        if (employee.getManager() != null) {
            employeeDto.setManagerDto(modelMapper.map(employee.getManager(), ManagerDto.class));
        }
        return employeeDto;
    }
    public Optional<EmployeeDto> updateEmployeePassword(String username, String newPassword) {
        Employee existingEmployee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with username: " + username));

        // Encrypt the new password
        existingEmployee.setPassword(passwordEncoder.encode(newPassword));

        try {
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return Optional.of(convertToDto(updatedEmployee));
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee password", e);
        }
    }
}