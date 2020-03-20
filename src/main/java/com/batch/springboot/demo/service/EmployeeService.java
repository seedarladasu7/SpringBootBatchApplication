package com.batch.springboot.demo.service;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RoiRequest;

public interface EmployeeService {
	
	public Employee getEmployee(int emId);
	
	public String registerRateOfInterest(RoiRequest roiRequest);
	
	public String applyForLoan(LoanRequest loanRequest);

}
