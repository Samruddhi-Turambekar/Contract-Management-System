package com.example.Contract_Management.repository;

import com.example.Contract_Management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByEmployeeUserId(Long userId);
}