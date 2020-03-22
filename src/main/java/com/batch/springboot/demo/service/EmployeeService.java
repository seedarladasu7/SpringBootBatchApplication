package com.batch.springboot.demo.service;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.entity.LoanAccount;
import com.batch.springboot.demo.entity.LoanEmiDetails;
import com.batch.springboot.demo.model.EmployeeLoanDetails;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RateOfInterestRequest;

public interface EmployeeService {
	
	public Employee getEmployee(int emId);
	
	public String registerRateOfInterest(RateOfInterestRequest roiRequest);
	
	public String applyForLoan(LoanRequest loanRequest);
	
	public LoanEmiDetails diductEmi(LoanAccount loanAccount);
	
	public EmployeeLoanDetails viewLoanDetails(String empId);

}
