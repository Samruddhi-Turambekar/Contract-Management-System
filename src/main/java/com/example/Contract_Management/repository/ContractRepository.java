package com.example.Contract_Management.repository;

import com.example.Contract_Management.model.Contract;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}