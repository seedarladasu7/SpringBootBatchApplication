package com.batch.springboot.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batch.springboot.demo.entity.RateOfInterest;
import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.model.LoanRequest;
import com.batch.springboot.demo.model.RoiRequest;
import com.batch.springboot.demo.repository.EmployeeRepository;
import com.batch.springboot.demo.repository.RoiRepository;
import com.batch.springboot.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository empRepository;

	@Autowired
	RoiRepository roiRepository;

	@Override
	public Employee getEmployee(int emId) {
		Optional<Employee> empOpt = empRepository.findById(emId);
		if (empOpt.isPresent())
			return empOpt.get();
		return null;
	}

	@Override
	public String registerRateOfInterest(RoiRequest roiRequest) {

		RateOfInterest roi = new RateOfInterest();

		roi.setSalaryFrom(roiRequest.getSalaryFrom());
		roi.setSalaryTo(roiRequest.getSalaryTo());
		roi.setRoi(roiRequest.getRoi());
		
		roiRepository.save(roi);

		return "Rate Of Interest registered successfully..";
	}

	@Override
	public String applyForLoan(LoanRequest loanRequest) {
		
		Employee emp = getEmployee(loanRequest.getEmpId());
		
		if(Optional.ofNullable(emp).isPresent()) {
			RateOfInterest roi = roiRepository.findByRoiBetweenLoanFromAndLoanTo(emp.getSalary());
			if(Optional.ofNullable(roi).isPresent()) {
				
				
				System.out.println(roi);
			} 
		} 
		
		RateOfInterest roi = roiRepository.findByRoiBetweenLoanFromAndLoanTo(emp.getSalary());
		
		if(Optional.ofNullable(roi).isPresent()) {
			System.out.println(roi);
		} 
		

		return null;
	}

}
