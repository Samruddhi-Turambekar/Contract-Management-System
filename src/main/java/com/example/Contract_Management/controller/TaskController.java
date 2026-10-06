package com.example.Contract_Management.controller;

import com.example.Contract_Management.model.Task;
import com.example.Contract_Management.model.Contract;
import com.example.Contract_Management.model.User;
import com.example.Contract_Management.repository.TaskRepository;
import com.example.Contract_Management.repository.ContractRepository;
import com.example.Contract_Management.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public TaskController(
            TaskRepository taskRepository,
            ContractRepository contractRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/employee/{userId}")
    public List<Task> getEmployeeTasks(@PathVariable Long userId) {
        return taskRepository.findByEmployeeUserId(userId);
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {

        Long contractId = task.getContract().getContractId();

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        task.setContract(contract);

        Long employeeId = task.getEmployee().getUserId();

        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getRole() != com.example.Contract_Management.model.Role.EMPLOYEE) {
            throw new RuntimeException("Selected user is not an employee");
        }

        task.setEmployee(employee);
        task.setAssignedDate(LocalDate.now());

        if (task.getStatus() == null || task.getStatus().isBlank()) {
            task.setStatus("PENDING");
        }

        return taskRepository.save(task);
    }

    /*
     * Employee:
     * PENDING -> IN PROGRESS
     *
     * Manager:
     * PENDING / IN PROGRESS -> COMPLETED
     */
    @PutMapping("/{taskId}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam String status,
            @RequestHeader("X-User-Id") Long userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String currentStatus = task.getStatus();

        // Employee permissions
        if (user.getRole() == com.example.Contract_Management.model.Role.EMPLOYEE) {

            // Employee can only update their own task
            if (!task.getEmployee().getUserId().equals(userId)) {
                return ResponseEntity.status(403)
                        .body("You can update only your own tasks");
            }

            // Employee cannot complete task
            if ("COMPLETED".equals(status)) {
                return ResponseEntity.status(403)
                        .body("Only manager can mark a task as COMPLETED");
            }

            // Employee can move task to IN PROGRESS
            if ("IN PROGRESS".equals(status)
                    && ("PENDING".equals(currentStatus)
                    || "IN PROGRESS".equals(currentStatus))) {

                task.setStatus(status);
                return ResponseEntity.ok(taskRepository.save(task));
            }

            return ResponseEntity.status(403)
                    .body("Invalid status change");
        }

        // Manager permissions
        if (user.getRole() == com.example.Contract_Management.model.Role.MANAGER) {

            if (!status.equals("PENDING")
                    && !status.equals("IN PROGRESS")
                    && !status.equals("COMPLETED")) {

                return ResponseEntity.badRequest()
                        .body("Invalid status");
            }

            task.setStatus(status);

            return ResponseEntity.ok(taskRepository.save(task));
        }

        return ResponseEntity.status(403)
                .body("Access denied");
    }
}