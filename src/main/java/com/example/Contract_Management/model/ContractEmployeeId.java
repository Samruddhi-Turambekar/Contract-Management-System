package com.example.Contract_Management.model;

import java.io.Serializable;
import java.util.Objects;

public class ContractEmployeeId implements Serializable {

    private Long contract;
    private Long employee;

    public ContractEmployeeId() {
    }

    public ContractEmployeeId(Long contract, Long employee) {
        this.contract = contract;
        this.employee = employee;
    }

    public Long getContract() {
        return contract;
    }

    public void setContract(Long contract) {
        this.contract = contract;
    }

    public Long getEmployee() {
        return employee;
    }

    public void setEmployee(Long employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ContractEmployeeId)) return false;

        ContractEmployeeId that = (ContractEmployeeId) o;

        return Objects.equals(contract, that.contract)
                && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, employee);
    }
}