package com.example.Contract_Management.controller;

import com.example.Contract_Management.model.Contract;
import com.example.Contract_Management.model.Company;
import com.example.Contract_Management.model.User;
import com.example.Contract_Management.repository.ContractRepository;
import com.example.Contract_Management.repository.CompanyRepository;
import com.example.Contract_Management.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractRepository contractRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public ContractController(
            ContractRepository contractRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository) {

        this.contractRepository = contractRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    // Get all contracts
    @GetMapping
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    // Add new contract
    @PostMapping
    public Contract addContract(@RequestBody Contract contract) {

        Long companyId =
                contract.getCompany().getCompanyId();

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException("Company not found"));

        contract.setCompany(company);

        Long userId =
                contract.getCreatedBy().getUserId();

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        contract.setCreatedBy(user);

        contract.setCreatedAt(LocalDateTime.now());

        return contractRepository.save(contract);
    }

    // Update contract status
    @PutMapping("/{contractId}/status")
    public Contract updateContractStatus(
            @PathVariable Long contractId,
            @RequestParam String status) {

        Contract contract =
                contractRepository.findById(contractId)
                        .orElseThrow(() ->
                                new RuntimeException("Contract not found"));

        contract.setStatus(status);

        return contractRepository.save(contract);
    }
}