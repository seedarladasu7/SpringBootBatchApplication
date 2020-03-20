package com.batch.springboot.demo.service;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RateOfInterestRequest;

public interface EmployeeService {
	
	public Employee getEmployee(int emId);
	
	public String registerRateOfInterest(RateOfInterestRequest roiRequest);
	
	public String applyForLoan(LoanRequest loanRequest);

}
