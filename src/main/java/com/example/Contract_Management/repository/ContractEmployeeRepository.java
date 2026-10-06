package com.example.Contract_Management.repository;

import com.example.Contract_Management.model.ContractEmployee;
import com.example.Contract_Management.model.ContractEmployeeId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractEmployeeRepository
        extends JpaRepository<ContractEmployee, ContractEmployeeId> {

    List<ContractEmployee> findByEmployeeUserId(Long userId);
}