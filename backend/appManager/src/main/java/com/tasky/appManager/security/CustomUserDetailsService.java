package com.tasky.appManager.security;
import com.tasky.appManager.domaine.Admin;
import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.domaine.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import com.tasky.appManager.reposetories.AdminRepo;
import com.tasky.appManager.reposetories.ManagerRepo;
import com.tasky.appManager.reposetories.EmployeeRepo;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private ManagerRepo managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return new User(admin.get().getUsername(), admin.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isPresent()) {
            return new User(employee.get().getUsername(), employee.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        Optional<Manager> manager = managerRepository.findByUsername(username);
        if (manager.isPresent()) {
            return new User(manager.get().getUsername(), manager.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER")));
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}