package com.tasky.appManager.util;

import com.tasky.appManager.domaine.Admin;
import com.tasky.appManager.reposetories.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultingAdmin implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        if (adminRepository.findAll().isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Test1*"));
            adminRepository.save(admin);
        }
    }
}
