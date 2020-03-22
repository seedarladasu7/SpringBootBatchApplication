package com.batch.springboot.demo.model;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeLoanDetails {
	private String empId;
	private List<LoanAccountDTO> loanAccounts;
}
