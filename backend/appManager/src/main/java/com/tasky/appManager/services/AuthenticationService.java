package com.tasky.appManager.services;

import com.tasky.appManager.domaine.*;
import com.tasky.appManager.reposetories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AdminRepo adminRepository;
    @Autowired
    private ManagerRepo managerRepository;
    @Autowired
    private EmployeeRepo employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) {
        Optional<User> user = findUserByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    private Optional<User> findUserByUsername(String username) {
        Optional<User> user;
        user = adminRepository.findByUsername(username).map(admin -> admin);
        if (user.isEmpty()) {
            user = managerRepository.findByUsername(username).map(manager -> manager);
        }
        if (user.isEmpty()) {
            user = employeeRepository.findByUsername(username).map(employee -> employee);
        }
        return user;
    }
}
