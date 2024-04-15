package com.tasky.appManager.reposetories;

import com.tasky.appManager.domaine.Employee;
import com.tasky.appManager.domaine.Manager;
import com.tasky.appManager.domaine.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByAssignedBy(Manager manager);

    List<Task> findByAssignedTo(Employee employee);
}
