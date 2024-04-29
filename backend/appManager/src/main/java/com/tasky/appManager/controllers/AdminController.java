package com.tasky.appManager.controllers;

import com.tasky.appManager.domaine.Manager;
import com.tasky.appManager.dtos.ManagerDto;
import com.tasky.appManager.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ManagerService managerService;

    @Autowired
    public AdminController(ManagerService managerService) {
        this.managerService = managerService;
    }

    // Manager creation
    @PostMapping("/createManager")
    public ResponseEntity<Object> createManager(@RequestBody Manager manager) {
        try {
            return ResponseEntity.ok(managerService.createManager(manager));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all managers
    @GetMapping("/getAllManagers")
    public ResponseEntity<List<ManagerDto>> getAllManagers() {
        List<ManagerDto> managers = managerService.getAllManagers();
        if (managers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(managers);
    }

    // Find manager by username
    @GetMapping("/findManager")
    public ResponseEntity<ManagerDto> findManagerByUsername(@RequestParam String username) {
        Optional<Manager> manager = managerService.findManagerByUsername(username);
       if (manager.isPresent()) {
            ManagerDto managerDto = managerService.convertToDto(manager);
            return ResponseEntity.ok(managerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update manager
    @PutMapping("/updateManager")
    public ResponseEntity<Object> updateManager(@RequestBody Manager manager) {
        System.out.println(manager);
        try {
            ManagerDto updatedManager = managerService.updateManager(manager);
            return ResponseEntity.ok(updatedManager);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete manager
    @DeleteMapping("/deleteManager")
    public ResponseEntity<Object> deleteManager(@RequestParam Long managerId) {
        try {
            managerService.deleteManager(managerId);
            return ResponseEntity.ok("Manager deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
