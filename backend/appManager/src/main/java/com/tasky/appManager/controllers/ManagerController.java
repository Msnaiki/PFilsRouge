package com.tasky.appManager.controllers;

import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.dtos.EmployeeDto;
import com.tasky.appManager.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final EmployeeService employeeService;

    @Autowired
    public ManagerController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating employee: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allEmployees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<Object> getEmployeeById(@RequestParam Long employeeId) {
        try {
            EmployeeDto employee = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving employee: " + e.getMessage());
        }
    }

    @PatchMapping("/updateEmployee")
    public ResponseEntity<Object> updateEmployeeBestQuality(@RequestBody EmployeeDto employeeDto) {
        try {
            Optional<EmployeeDto> updatedEmployee = employeeService.updateEmployeeBestQuality(employeeDto);
            return updatedEmployee.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with ID: " + employeeDto.getEmployeeId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<String> deleteEmployeeById(@RequestParam Long employeeId) {
        try {
            employeeService.deleteEmployeeById(employeeId);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting employee: " + e.getMessage());
        }
    }
}
