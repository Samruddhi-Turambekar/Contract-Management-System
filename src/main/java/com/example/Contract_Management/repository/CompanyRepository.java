package com.example.Contract_Management.repository;

import com.example.Contract_Management.model.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}