package com.tasky.appManager.reposetories;

import com.tasky.appManager.domaine.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepo extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUsername(String username);

}
