package com.example.Contract_Management.controller;

import com.example.Contract_Management.model.Contract;
import com.example.Contract_Management.model.ContractEmployee;
import com.example.Contract_Management.model.User;

import com.example.Contract_Management.repository.ContractEmployeeRepository;
import com.example.Contract_Management.repository.ContractRepository;
import com.example.Contract_Management.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/assignments")
public class ContractEmployeeController {

    private final ContractEmployeeRepository contractEmployeeRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    public ContractEmployeeController(
            ContractEmployeeRepository contractEmployeeRepository,
            ContractRepository contractRepository,
            UserRepository userRepository) {

        this.contractEmployeeRepository = contractEmployeeRepository;
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<ContractEmployee> getAllAssignments() {
        return contractEmployeeRepository.findAll();
    }

    @GetMapping("/employee/{userId}")
    public List<ContractEmployee> getEmployeeAssignments(
            @PathVariable Long userId) {

        return contractEmployeeRepository.findByEmployeeUserId(userId);
    }

    @PostMapping
    public ContractEmployee assignEmployee(
            @RequestBody ContractEmployee assignment) {

        Long contractId =
                assignment.getContract().getContractId();

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() ->
                        new RuntimeException("Contract not found"));

        Long employeeId =
                assignment.getEmployee().getUserId();

        User employee = userRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        assignment.setContract(contract);
        assignment.setEmployee(employee);
        assignment.setAssignedDate(LocalDate.now());

        return contractEmployeeRepository.save(assignment);
    }
}